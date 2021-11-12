package metier.strategy.spoon;

import bootstrap.parsers.spoon.SpoonASTParser;
import metier.graphs.normal.Graph;
import metier.graphs.utils.HelperGraph;
import metier.strategy.StaticCallGraphCreatorStrategy;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtTypeInformation;
import visitors.spoon.ClassDeclarationsCollector;
import visitors.spoon.InterfaceDeclarationsCollector;
import visitors.spoon.MethodDeclarationsCollector;
import visitors.spoon.MethodInvocationsCollector;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SpoonStaticCallGraphStrategy implements StaticCallGraphCreatorStrategy {


    private static SpoonStaticCallGraphStrategy instance;

    private SpoonASTParser parser;

    private CtModel ctModel;

    private Set<String> classes = new HashSet<>();


    public static SpoonStaticCallGraphStrategy getInstance(SpoonASTParser parser){
        if (instance == null) {
            instance = new SpoonStaticCallGraphStrategy();
            instance.setParser(parser);
            instance.setCtModel(parser);
        }
        return instance;
    }

    public void setParser(SpoonASTParser parser) {
        this.parser = parser;
    }

    public void setCtModel(SpoonASTParser parser) { this.ctModel = parser.createFAMIXModel(); }

    public void setClasses(ClassDeclarationsCollector classDeclarationsCollector,
                           InterfaceDeclarationsCollector interfaceDeclarationsCollector) {

        classes = classDeclarationsCollector.getClassDeclarations().stream().map(CtTypeInformation::getQualifiedName).collect(Collectors.toSet());

        Set<String> interfaces = interfaceDeclarationsCollector.getInterfaceDeclarations().stream().map(CtTypeInformation::getQualifiedName).collect(Collectors.toSet());

        classes.addAll(interfaces);

        System.out.println(classes);
    }

    @Override
    public Graph createCallGraph() throws IOException {
        Graph graph = new Graph();



        ClassDeclarationsCollector classDeclarationsCollector = new ClassDeclarationsCollector(ctModel);
        InterfaceDeclarationsCollector interfaceDeclarationsCollector = new InterfaceDeclarationsCollector(ctModel);

        setClasses(classDeclarationsCollector, interfaceDeclarationsCollector);

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
                        if ( !classDeclarationOfMethodInvocationName.equals(ctClass.getQualifiedName())
                                && (classes.contains(classDeclarationOfMethodInvocationName))
                           )
                        {

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

    public Set<String> getClasses() {
        return classes;
    }
}
