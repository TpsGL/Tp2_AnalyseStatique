package responsesForQuestions.graphAppel;


import bootstrap.ASTCreator;
import calculators.MethodCounter;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import visitors.MethodInvocationVisitor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CallGraph {

    private static CallGraph instance;
    private List<String> methodInvocations = new ArrayList<>();
    private final String path = "src/main/resources/assets/";
    private String fileName;
    private static final String dotExtention = ".dot";
    private static final String pngExtention = ".png";
    private final String dotFilePath;
    private final String pngFilePath;

    private Map<String, Map<String, Map<String, String>>> invocations = new HashMap<>();


    private CallGraph(String fileName) {
        this.fileName = fileName;
        dotFilePath = path + fileName + dotExtention;
        pngFilePath = path + fileName + pngExtention;
    }

    public static CallGraph getInstance(String fileName){
        if (instance == null) {
            instance = new CallGraph(fileName);
        }
        return instance;
    }


    public void createCallGraph(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {
        createDotGraph(astCreator, javaFiles);
        System.out.println("!!! Dot File Successfully Generated !!!");
        transformDotGraphToPngImage();
    }

    private void createDotGraph(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        FileWriter writer = new FileWriter(dotFilePath);
        writer.write("digraph \"call-graph\" {\n");

        initGraphNode(astCreator, javaFiles);

        methodInvocations.stream().distinct().collect(Collectors.toList()).forEach(methodInvocation -> {
            try {
                writer.write(methodInvocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.write("}");
        writer.close();
    }

     private void transformDotGraphToPngImage() {
        try (InputStream dotFile = new FileInputStream(dotFilePath)) {
            MutableGraph mutableGraph = new Parser().read(dotFile);
            Graphviz.fromGraph(mutableGraph).width(10000).render(Format.PNG).toFile(new File(pngFilePath));
            System.out.println("!!! PNG Image Successfully Generated !!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initGraphNode(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        for (File javaFile : javaFiles) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            visitMethodInvocationIntoMethodDeclaration(cu);
        }
    }


    private void visitMethodInvocationIntoMethodDeclaration(CompilationUnit cu) {

        MethodCounter methodCounter = new MethodCounter();
        methodCounter.countItems(cu);

        for (MethodDeclaration method : methodCounter.getMethodDeclarations()) {

            MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
            method.accept(methodInvocationVisitor);


            for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {

                methodInvocations.add("\t" + "\"" + constructFullNameOfCallerMethod(method)
                        + "\"->\"" + constructFullNameOfInvokedMethod(methodInvocation) + "()\";\n");
            }
        }
    }

    private String constructFullNameOfCallerMethod(MethodDeclaration method) {

        String methodName = "";

        IMethodBinding resolveBinding = method.resolveBinding();
       if ((resolveBinding != null) && (resolveBinding.getDeclaringClass()!= null) ) {

            methodName += method.resolveBinding().getDeclaringClass().getName() + ".";
       }

        //methodName += method.getName().toString() + "()";

        return methodName;
    }

    private String constructFullNameOfInvokedMethod(MethodInvocation methodInvocation) {

        String invokedMethodName = "";

        Expression expression = methodInvocation.getExpression();

        if (expression != null) {

            ITypeBinding typeBinding = expression.resolveTypeBinding();
            invokedMethodName += typeBinding.getTypeDeclaration().getName() + ".";

        }

        invokedMethodName+= methodInvocation.getName();
        return invokedMethodName;
    }




}
