package responsesForQuestions.total;

import bootstrap.ASTCreator;
import calculators.ImportsCounter;
import calculators.PackageCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import visitors.ClassDeclarationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeLinesApp {

    private static CodeLinesApp instance;

    private CodeLinesApp() { }

    public static CodeLinesApp getInstance(){
        if (instance == null) {
            instance = new CodeLinesApp();
        }
        return instance;
    }

    /**
     *
     * @param astCreator
     * @param javaFiles
     * @return
     * @throws IOException
     */
    public int codeLinesNumberApp(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {

        AtomicInteger codeLinesNumber = new AtomicInteger();
        PackageCounter packageCounter = new PackageCounter();
        ImportsCounter importsCounter = new ImportsCounter();

        ClassDeclarationVisitor classDeclarationVisitor = new ClassDeclarationVisitor();

        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(classDeclarationVisitor);
            classDeclarationVisitor.getClasses().forEach(typeDeclaration -> {
                int start =  cu.getLineNumber(typeDeclaration.getStartPosition()) ;
                int end = cu.getLineNumber(typeDeclaration.getStartPosition() + typeDeclaration.getLength() - 1);
                codeLinesNumber.addAndGet(Math.max((end - start), 0));
            });

            codeLinesNumber.addAndGet((packageCounter.countItems(cu) + importsCounter.countItems(cu) + cu.getCommentList().size()))
            ;
        }

        return codeLinesNumber.get();
    }
}
