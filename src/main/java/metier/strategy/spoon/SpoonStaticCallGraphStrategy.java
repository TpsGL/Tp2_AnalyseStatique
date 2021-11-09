package metier.strategy.spoon;

import bootstrap.parsers.spoon.SpoonASTParser;
import metier.graphs.normal.Graph;
import metier.graphs.utils.HelperGraph;
import metier.strategy.StaticCallGraphCreatorStrategy;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import visitors.spoon.ClassDeclarationsCollector;
import visitors.spoon.MethodDeclarationsCollector;
import visitors.spoon.MethodInvocationsCollector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpoonStaticCallGraphStrategy implements StaticCallGraphCreatorStrategy {


    private static SpoonStaticCallGraphStrategy instance;

    private SpoonASTParser parser;

    public static SpoonStaticCallGraphStrategy getInstance(SpoonASTParser parser){
        if (instance == null) {
            instance = new SpoonStaticCallGraphStrategy();
            instance.setParser(parser);
        }
        return instance;
    }

    public void setParser(SpoonASTParser parser) {
        this.parser = parser;
    }



    @Override
    public Graph createCallGraph() throws IOException {
        Graph graph = new Graph();

        CtModel ctModel = parser.createFAMIXModel();
        ClassDeclarationsCollector classDeclarationsCollector = new ClassDeclarationsCollector(ctModel);
        MethodDeclarationsCollector methodDeclarationsCollector = MethodDeclarationsCollector.getInstance();
        MethodInvocationsCollector methodInvocationsCollector = MethodInvocationsCollector.getInstance();
        HelperGraph helperGraph = HelperGraph.getInstance();


        for(CtClass ctClass: classDeclarationsCollector.getClassDeclarations()) {

            Map<String, Map<String, String>> methodsInvocations = new HashMap<>();
            Map<String, String> targetClassesInvocations = new HashMap<>();

            for (CtMethod ctMethod : methodDeclarationsCollector.getMethodsOfClass(ctClass)) {

                Map<String, String> tmpMap = new HashMap<>();


                if(methodInvocationsCollector.getMethodsInvocation(ctMethod).size() > 0) {

                    for (CtInvocation ctInvocation: methodInvocationsCollector.getMethodsInvocation(ctMethod)) {

                        String classDeclarationOfMethodInvocationName =
                                helperGraph.getClassDeclarationOfMethodInvocationName(ctInvocation);
                        if (!classDeclarationOfMethodInvocationName.equals(ctClass.getQualifiedName())) {

                            tmpMap.put(  helperGraph.getMethodInvocationName(ctInvocation), classDeclarationOfMethodInvocationName);
                        }
                    }

                    targetClassesInvocations = tmpMap;

                    methodsInvocations.put( ctMethod.getSimpleName() + ctMethod.getParameters() , targetClassesInvocations);

                    graph.getSourceClassesInvocations().put( ctClass.getQualifiedName(), methodsInvocations);

                }
            }

        }

        return graph;
    }
}
