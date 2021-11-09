package metier.strategy.jdt;

import bootstrap.parsers.jdt.EclipseJDTASTParser;
import metier.graphs.normal.Graph;
import metier.graphs.utils.HelperGraph;
import metier.strategy.StaticCallGraphCreatorStrategy;
import org.eclipse.jdt.core.dom.*;
import visitors.jdt.ClassDeclarationVisitor;
import visitors.jdt.MethodCounter;
import visitors.jdt.MethodInvocationVisitor;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EclipseJDTStaticCallGraphStrategy implements StaticCallGraphCreatorStrategy {


    private static EclipseJDTStaticCallGraphStrategy instance;

    private EclipseJDTASTParser parser;

    private Set<String> classes = new HashSet<>();

    public static EclipseJDTStaticCallGraphStrategy getInstance(EclipseJDTASTParser parser){
        if (instance == null) {
            instance = new EclipseJDTStaticCallGraphStrategy();
            instance.setParser(parser);
        }
        return instance;
    }

    public void setParser(EclipseJDTASTParser parser) {
        this.parser = parser;
    }


    private void setClasses() throws IOException {

        ClassDeclarationVisitor classDeclarationVisitor = new ClassDeclarationVisitor();

        for (CompilationUnit cu: parser.parseProject()) {
            cu.accept(classDeclarationVisitor);

            classes.addAll(
                    classDeclarationVisitor
                            .getClasses()
                            .stream()
                            .map(e -> e.getName().getIdentifier())
                            .collect(Collectors.toList())
            );
        }


    }

    public Set<String> getClasses() {
        return classes;
    }

    @Override
    public Graph createCallGraph() {

        Graph graph = new Graph();

        try {
            setClasses();

            System.err.println(classes);
            for (CompilationUnit cu: parser.parseProject()) {

                visitMethodInvocationIntoMethodDeclaration(graph, cu);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

            if (methodInvocationVisitor.getMethods().size() > 0) {

                for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {

                    String classDeclarationOfMethodInvocationName =
                            helperGraph.getClassDeclarationOfMethodInvocationName(methodInvocation);

                    if (!classDeclarationOfMethodInvocationName.equals(classDeclarationName)

                           && (classes.contains(classDeclarationOfMethodInvocationName))
                    ) {

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
