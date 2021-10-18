package calculators;

import org.eclipse.jdt.core.dom.CompilationUnit;
import visitors.ImportDeclarationVisitor;

public class ImportsCounter implements ICounter{

    @Override
    public int countItems(CompilationUnit parse) {
        ImportDeclarationVisitor importDeclarationVisitor =
                new ImportDeclarationVisitor();
        parse.accept(importDeclarationVisitor);
        return importDeclarationVisitor.getImports().size();
    }
}
