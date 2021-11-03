package responsesForQuestions.moyen;

import bootstrap.jdt.ASTCreator;
import calculators.CodeLinesPerMethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import responsesForQuestions.total.MethodsApp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CodeLinesPerMethodApp {

    private static CodeLinesPerMethodApp instance;

    private CodeLinesPerMethodApp() { }

    public static CodeLinesPerMethodApp getInstance() {
        if (instance == null) { instance = new CodeLinesPerMethodApp(); }
        return instance;
    }

    public double averageNumberOfCodeLinesPerMethod(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        int numberTotalMethod = MethodsApp.getInstance().methodNumber(astCreator,javaFiles);

        int linesCodeNumberPerMethod = 0;

        CodeLinesPerMethodCounter codeLinesPerMethodCounter = new CodeLinesPerMethodCounter();

        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());

            linesCodeNumberPerMethod += codeLinesPerMethodCounter.countItems(cu);
        }
        return  (double) ( (double)  linesCodeNumberPerMethod / (double) numberTotalMethod )  ;
    }


}
