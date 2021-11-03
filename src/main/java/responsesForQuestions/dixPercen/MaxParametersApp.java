package responsesForQuestions.dixPercen;

import bootstrap.jdt.ASTCreator;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MaxParametersApp {

    private static MaxParametersApp instance;

    private MaxParametersApp() { }

    public static MaxParametersApp getInstance() {
        if (instance == null) { instance = new MaxParametersApp(); }
        return instance;
    }

    public int maxParametersNumber(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        MethodCounter methodCounter = new MethodCounter();
        int maxParams = 0;
        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());

            methodCounter.countItems(cu);

            for (MethodDeclaration methodDeclaration : methodCounter.getMethodDeclarations()) {
                if (methodDeclaration.parameters().size() > maxParams) {
                    System.out.println(methodDeclaration.getName().getIdentifier());
                    maxParams = methodDeclaration.parameters().size();
                }
            }

        }

        return maxParams;
    }

}
