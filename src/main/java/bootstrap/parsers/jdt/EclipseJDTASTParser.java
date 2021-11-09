package bootstrap.parsers.jdt;

import bootstrap.parsers.parser.Parser;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EclipseJDTASTParser extends Parser<ASTParser> {


    public EclipseJDTASTParser(String projectSourcePath, String jrePath) {
        super(projectSourcePath, jrePath);
    }

    public void setParser() {
        parser = ASTParser.newParser(AST.JLS8); // java +1.6
        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        parser.setBindingsRecovery(true);

        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);

        parser.setUnitName("");

        String[] sources = { projectPath };
        String[] classpath = {jrePath};

        parser.setEnvironment(classpath, sources, new String[] { "UTF-8" }, true);
    }

    // create AST
    public CompilationUnit parse(char[] classSource) {

        setParser();

        parser.setSource(classSource);

        // create and parse
        return (CompilationUnit) parser.createAST(null);
    }

    public List<CompilationUnit> parseProject() throws IOException {

        List<CompilationUnit> cUnits = new ArrayList<>();

        for (File sourceFile: listJavaProjectFiles())
            cUnits.add(parse(FileUtils.readFileToString(sourceFile).toCharArray()));

        return cUnits;
    }
}
