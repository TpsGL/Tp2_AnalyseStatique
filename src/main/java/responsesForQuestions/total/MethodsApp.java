package responsesForQuestions.total;

import bootstrap.ASTCreator;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MethodsApp {

    private static MethodsApp instance;

    private MethodsApp() { }


    public static MethodsApp getInstance(){
        if (instance == null) {
            instance = new MethodsApp();
        }
        return instance;
    }

    public int methodNumber(ASTCreator astCreator, ArrayList<File> javaFiles)  throws IOException {

        MethodCounter methodCounter = new MethodCounter();
        int methodCpt = 0;
        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());

            methodCpt += methodCounter.countItems(cu);
        }

        return methodCpt;

    }
}
