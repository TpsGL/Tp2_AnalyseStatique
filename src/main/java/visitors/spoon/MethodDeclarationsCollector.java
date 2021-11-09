package visitors.spoon;

import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.List;
import java.util.Set;

public class MethodDeclarationsCollector{

    private static MethodDeclarationsCollector instance;

    private MethodDeclarationsCollector() { }

    public static MethodDeclarationsCollector getInstance() {
        if (instance == null) {
            instance = new MethodDeclarationsCollector();
        }
        return instance;
    }

    public Set<CtMethod> getMethodsOfClass(CtClass ctClass) {
        return ctClass.getMethods();
    }



}
