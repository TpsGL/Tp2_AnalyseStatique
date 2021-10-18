package responsesForQuestions.moyen;

import bootstrap.ASTCreator;
import calculators.AttributesCounter;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import responsesForQuestions.total.AttributesApp;
import responsesForQuestions.total.ClassesApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttributesPerClassApp {

    private static AttributesPerClassApp instance;

    private AttributesPerClassApp() { }

    public static AttributesPerClassApp getInstance() {
        if (instance == null) { instance = new AttributesPerClassApp(); }
        return instance;
    }

    public double averageNumberOfAttributesPerClass(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        int numberDeClasses = ClassesApp.getInstance().classesNumber(astCreator, javaFiles);
        AttributesCounter attributesCounter = new AttributesCounter();
        int numberAttributesPerClass = 0;

        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            numberAttributesPerClass += attributesCounter.countItems(cu);
        }
        return  (double) ( (double)  numberAttributesPerClass / (double) numberDeClasses )   ;

    }




}
