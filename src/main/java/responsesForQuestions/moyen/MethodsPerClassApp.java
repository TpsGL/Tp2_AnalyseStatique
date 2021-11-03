package responsesForQuestions.moyen;

import bootstrap.jdt.ASTCreator;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import responsesForQuestions.total.ClassesApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MethodsPerClassApp {

    private static MethodsPerClassApp instance;

    private MethodsPerClassApp() { }

    public static MethodsPerClassApp getInstance(){
        if (instance == null) {
            instance = new MethodsPerClassApp();
        }
        return instance;
    }



    public double averageNumberOfMethodsPerClass(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        int numberDeClasses = ClassesApp.getInstance().classesNumber(astCreator, javaFiles);
        MethodCounter methodCounter = new MethodCounter();

        int methodsPerClass = 0;

        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            methodsPerClass += methodCounter.countItems(cu);
        }
        return (double) ( (double)  methodsPerClass / (double) numberDeClasses )  ;
    }
}
