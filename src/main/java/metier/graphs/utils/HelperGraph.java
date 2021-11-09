package metier.graphs.utils;

import org.eclipse.jdt.core.dom.*;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;

public class HelperGraph {


    private static HelperGraph instance;

    private HelperGraph() { }

    public static HelperGraph getInstance(){
        if (instance == null) {
            instance = new HelperGraph();
        }
        return instance;
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

        return methodDeclaration.getName().getFullyQualifiedName() + methodDeclaration.parameters();
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


           if (typeBinding != null) {
                return typeBinding.getTypeDeclaration().getName();
           }

        }

        else {
            IMethodBinding methodDeclaration = methodInvocation.resolveMethodBinding();

                return methodDeclaration.getDeclaringClass().getName();

        }

        return "";
    }

    public String getClassDeclarationOfMethodInvocationName(CtInvocation ctInvocation){

        if (ctInvocation.getTarget().getType().getSimpleName()!= "void") {
            return ctInvocation.getTarget().getType().getSimpleName();
        }

        return getCurrentClassInvocation(ctInvocation);
    }

    /**
     *
     * @param ctInvocation
     * @return
     */
    public String getCurrentClassInvocation(CtInvocation ctInvocation) {

        CtElement parent = null;
        CtClass currentClass = null;

        // get the current class
        parent = ctInvocation.getParent();

        while (!(parent instanceof CtClass)) {
            parent = parent.getParent();
        }

        currentClass = (CtClass) parent;
        return currentClass.getQualifiedName();
    }


    public static String getClassFullyQualifiedName(TypeDeclaration typeDeclaration) {
        String name = typeDeclaration.getName().getIdentifier();

        if (typeDeclaration.getRoot().getClass() == CompilationUnit.class) {
            CompilationUnit root = (CompilationUnit) typeDeclaration.getRoot();

            if (root.getPackage() != null)
                name = root.getPackage().getName().getFullyQualifiedName() + "." + name;
        }

        return name;
    }

    public String getMethodInvocationName(MethodInvocation methodInvocation) {

        return methodInvocation.getName().getFullyQualifiedName();
    }

    public String getMethodInvocationName(CtInvocation methodInvocation) {
        if (methodInvocation.getExecutable().getDeclaration() instanceof CtMethod) {
            return ((CtMethod)methodInvocation.getExecutable().getDeclaration()).getSimpleName() ; //+
                    //((CtMethod)methodInvocation.getExecutable().getDeclaration()).getParameters();
        }
        return methodInvocation.toString();
    }





}
