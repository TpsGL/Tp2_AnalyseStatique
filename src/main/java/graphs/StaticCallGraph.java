package graphs;

import bootstrap.jdt.ASTCreator;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import visitors.MethodInvocationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StaticCallGraph {

    private static StaticCallGraph instance;

    public static StaticCallGraph getInstance(){
        if (instance == null) {
            instance = new StaticCallGraph();
        }
        return instance;
    }



    public Graph createCallGraph(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        Graph graph = new Graph();

        for (File javaFile : javaFiles) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            visitMethodInvocationIntoMethodDeclaration(graph, cu);
        }

        return graph;
    }




    private void visitMethodInvocationIntoMethodDeclaration(Graph graph,CompilationUnit cu) {

        HelperGraph helperGraph = HelperGraph.getInstance();
        String classDeclarationName = "";
        Map<String, Map<String, String>> methodsInvocations = new HashMap<>();
        Map<String, String> targetClassesInvocations = new HashMap<>();

        MethodCounter methodCounter = new MethodCounter();
        methodCounter.countItems(cu);


        for (MethodDeclaration method : methodCounter.getMethodDeclarations()) {
            Map<String, String> tmpMap = new HashMap<>();


            classDeclarationName = helperGraph.getClassDeclarationName(method);

            MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
            method.accept(methodInvocationVisitor);


            if (methodInvocationVisitor.getMethods().size() != 0) {


                for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {

                    String classDeclarationOfMethodInvocationName =
                            helperGraph.getClassDeclarationOfMethodInvocationName(methodInvocation);

                    if (!classDeclarationOfMethodInvocationName.equals(classDeclarationName)) {

                        tmpMap.put( helperGraph.getMethodInvocationName(methodInvocation),
                                classDeclarationOfMethodInvocationName);
                    }

                }

                targetClassesInvocations = tmpMap;

                methodsInvocations.put( helperGraph.getMethodDeclarationName(method), targetClassesInvocations);

                graph.getSourceClassesInvocations().put( helperGraph.getClassDeclarationName(method), methodsInvocations);

            }

        }

    }








}
