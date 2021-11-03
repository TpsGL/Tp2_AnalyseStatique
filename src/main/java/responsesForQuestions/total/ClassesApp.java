package responsesForQuestions.total;

import bootstrap.jdt.ASTCreator;
import calculators.ClassCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClassesApp {

    private static ClassesApp instance;

    private ClassesApp() { }

    public static ClassesApp getInstance(){
       if (instance == null) {
           instance = new ClassesApp();
       }
       return instance;
    }

    public int classesNumber(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {
        ClassCounter classCounter = new ClassCounter();

        int classCpt = 0;
        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            classCpt += classCounter.countItems(cu);
        }

        return classCpt;
    }

}
