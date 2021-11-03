package responsesForQuestions.dixPercen;

import bootstrap.jdt.ASTCreator;
import calculators.AttributesCounter;
import calculators.ClassCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MostAttributesPerClassApp {

    private static MostAttributesPerClassApp instance;

    private MostAttributesPerClassApp() { }

    public static MostAttributesPerClassApp getInstance() {
        if (instance == null) { instance = new MostAttributesPerClassApp(); }
        return instance;
    }

    public List<String> classesThatHaveMostAttributesNumber(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        ClassCounter classCounter = new ClassCounter();
        HashMap<String, Integer> classesAttributesHashMap = new HashMap<String, Integer>();
        AttributesCounter attributesCounter = new AttributesCounter();

        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            classCounter.countItems(cu); // init classes list !
            // if there's an inner classes !!
            for(TypeDeclaration typeDeclaration: classCounter.getClasses()) {
                // Class ---> to method methodCounter.countItems(cu)
                classesAttributesHashMap.put(typeDeclaration.getName().toString(), typeDeclaration.getFields().length );
            }
        }

        // 10 % of classes
        int numberOfClasses = (int) Math.ceil(0.1 * classesAttributesHashMap.size());

        //
        List<String> classes = classesAttributesHashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        return classes.subList(0, numberOfClasses);
    }

}
