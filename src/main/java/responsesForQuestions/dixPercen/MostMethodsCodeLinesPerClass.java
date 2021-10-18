package responsesForQuestions.dixPercen;

import bootstrap.ASTCreator;
import calculators.ClassCounter;
import calculators.CodeLinesPerMethodCounter;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MostMethodsCodeLinesPerClass {

    private static MostMethodsCodeLinesPerClass instance;

    private MostMethodsCodeLinesPerClass() { }

    public static MostMethodsCodeLinesPerClass getInstance() {
        if (instance == null) { instance = new MostMethodsCodeLinesPerClass(); }
        return instance;
    }

    public List<String> methodsThatHaveMostCodeLinesNumber(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        ClassCounter classCounter = new ClassCounter();
        HashMap<String, Integer> methodsCodeLinesHashMap = new HashMap<String, Integer>();

        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            // init classes list !!!
            classCounter.countItems(cu);
            // if there's an inner classes !!!
            for(TypeDeclaration typeDeclaration: classCounter.getClasses()) {
                // Class ---> to method methodCounter.countItems(cu)

                for (MethodDeclaration methodDeclaration : typeDeclaration.getMethods()) {
                    methodsCodeLinesHashMap.put(
                            typeDeclaration.getName().toString()+ "."+ methodDeclaration.getName().getIdentifier(),
                            methodDeclaration.getBody().statements().size()
                    );
                }
            }
        }


        // 10 % of classes
        int numberOfClasses = (int) Math.ceil(0.1 * methodsCodeLinesHashMap.size());

        //
        List<String> methodes = methodsCodeLinesHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        return methodes.subList(0, numberOfClasses) ;
    }

}
