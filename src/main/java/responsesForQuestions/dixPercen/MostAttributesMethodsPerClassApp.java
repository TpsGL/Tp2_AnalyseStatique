package responsesForQuestions.dixPercen;

import bootstrap.jdt.ASTCreator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MostAttributesMethodsPerClassApp {

    private static MostAttributesMethodsPerClassApp instance;

    private MostAttributesMethodsPerClassApp() { }

    public static MostAttributesMethodsPerClassApp getInstance() {
        if (instance == null) { instance = new MostAttributesMethodsPerClassApp(); }
        return instance;
    }

    public List<String> classesThatHaveMostAttributesMethodsNumber(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        List<String> classesThatHaveMostAttributesNumber = MostAttributesPerClassApp.getInstance().classesThatHaveMostAttributesNumber(astCreator, javaFiles);
        List<String> classesThatHaveMostMethodsNumber = MostMethodsPerClassApp.getInstance().classesThatHaveMostMethodsNumber(astCreator, javaFiles);
        classesThatHaveMostAttributesNumber.retainAll(classesThatHaveMostMethodsNumber);
        return classesThatHaveMostAttributesNumber;
    }

}
