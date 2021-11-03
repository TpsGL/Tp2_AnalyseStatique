package responsesForQuestions.total;

import bootstrap.jdt.ASTCreator;
import calculators.AttributesCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AttributesApp {

    private static AttributesApp instance;

    private AttributesApp() { }

    public static AttributesApp getInstance(){
        if (instance == null) {
            instance = new AttributesApp();
        }
        return instance;
    }

    public int attributesNumber(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        AttributesCounter attributesCounter = new AttributesCounter();

        int attributCpt = 0;
        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            attributCpt += attributesCounter.countItems(cu);
        }

        return attributCpt;
    }
}
