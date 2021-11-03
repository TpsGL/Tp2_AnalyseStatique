package responsesForQuestions.dixPercen;

import bootstrap.jdt.ASTCreator;
import calculators.ClassCounter;
import calculators.MethodCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class XMethodsPerClassApp {

    private static XMethodsPerClassApp instance;

    private XMethodsPerClassApp() { }

    public static XMethodsPerClassApp getInstance() {
        if (instance == null) { instance = new XMethodsPerClassApp(); }
        return instance;
    }

    public List<String> classesThatHaveXMethodsNumber(ASTCreator astCreator, ArrayList<File> javaFiles, int X) throws IOException {

        ClassCounter classCounter = new ClassCounter();
        HashMap<String, Integer> classesMethodsHashMap = new HashMap<String, Integer>();
        MethodCounter methodCounter = new MethodCounter();

        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            classCounter.countItems(cu); // init classes list !
            // if there's an inner classes !!
            for(TypeDeclaration typeDeclaration: classCounter.getClasses()) {
                // Class ---> to method methodCounter.countItems(cu)
                if ( typeDeclaration.getMethods().length > X) {
                    classesMethodsHashMap.put(typeDeclaration.getName().toString(), typeDeclaration.getMethods().length );
                }
            }
        }


        //
        List<String> classes = classesMethodsHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        return classes ;
    }

}
