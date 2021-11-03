package responsesForQuestions.graphAppel;


import bootstrap.jdt.ASTCreator;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import visitors.MethodInvocationVisitor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallGraph {

    private static CallGraph instance;
    private List<String> methodInvocations = new ArrayList<>();
    private final String path = "src/main/resources/assets/";
    private String fileName;
    private static final String dotExtention = ".dot";
    private static final String pngExtention = ".png";
    private final String dotFilePath;
    private final String pngFilePath;

    /**
     * data structure
     */
    public Map<String, Map<String, Map<String, String>>> sourceClassesInvocations = new HashMap<>();




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

       // FileWriter writer = new FileWriter(dotFilePath);
       // writer.write("digraph \"call-graph\" {\n");

        initGraphNode(astCreator, javaFiles);

        /*methodInvocations.stream().distinct().collect(Collectors.toList()).forEach(methodInvocation -> {
            try {
                writer.write(methodInvocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.write("}");
        writer.close();*/
    }

     private void transformDotGraphToPngImage() {
        /*try (InputStream dotFile = new FileInputStream(dotFilePath)) {
            MutableGraph mutableGraph = new Parser().read(dotFile);
            Graphviz.fromGraph(mutableGraph).width(10000).render(Format.PNG).toFile(new File(pngFilePath));
            System.out.println("!!! PNG Image Successfully Generated !!!");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    private void initGraphNode(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        for (File javaFile : javaFiles) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            visitMethodInvocationIntoMethodDeclaration(cu);
           // System.out.println(sourceClassesInvocations);
        }

        System.out.println(sourceClassesInvocations);
    }

    /**
     * Get the name of the class which contains the method declaration
     * @param methodDeclaration
     * @return
     */
    public String getClassDeclarationName(MethodDeclaration methodDeclaration) {
        IMethodBinding resolveBinding = methodDeclaration.resolveBinding();
        if ((resolveBinding != null) && (resolveBinding.getDeclaringClass()!= null) ) {
           return methodDeclaration.resolveBinding().getDeclaringClass().getName();
        }
        return "";
    }

    /**
     * Get the name of method declaration
     * @param methodDeclaration
     * @return
     */
    public String getMethodDeclarationName(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getName().getFullyQualifiedName();
    }

    /**
     * Get the name of method invocation
     * @param methodInvocation
     * @return
     */
    public String getClassDeclarationOfMethodInvocationName(MethodInvocation methodInvocation) {
        Expression expression = methodInvocation.getExpression();
        if (expression != null) {
            ITypeBinding typeBinding = expression.resolveTypeBinding();
            return typeBinding.getTypeDeclaration().getName();
        }
        return "";
    }

    public String getMethodInvocationName(MethodInvocation methodInvocation) {
        return methodInvocation.getName().getFullyQualifiedName();
    }



    private void visitMethodInvocationIntoMethodDeclaration(CompilationUnit cu) {

        MethodCounter methodCounter = new MethodCounter();

        methodCounter.countItems(cu);

        String classDeclarationName = "";

        Map<String, Map<String, String>> methodsInvocations = new HashMap<>();

        Map<String, String> targetClassesInvocations = new HashMap<>();

        for (MethodDeclaration method : methodCounter.getMethodDeclarations()) {



            MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
            method.accept(methodInvocationVisitor);


            for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {


                targetClassesInvocations.put( getMethodInvocationName(methodInvocation),
                        getClassDeclarationOfMethodInvocationName(methodInvocation) );

            }


            methodsInvocations.put( getMethodDeclarationName(method), targetClassesInvocations);


            if (classDeclarationName != (getClassDeclarationName(method))) {
                sourceClassesInvocations.put( getClassDeclarationName(method), methodsInvocations);
                classDeclarationName = getClassDeclarationName(method);

            }

            targetClassesInvocations = new HashMap<>();
        }

    }




}
