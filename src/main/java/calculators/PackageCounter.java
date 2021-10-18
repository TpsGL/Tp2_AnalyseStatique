package calculators;

import org.eclipse.jdt.core.dom.CompilationUnit;
import visitors.PackageDeclarationVisitor;

public class PackageCounter implements ICounter{



    @Override
    public int countItems(CompilationUnit parse) {
        PackageDeclarationVisitor packageDeclarationVisitor = new PackageDeclarationVisitor();
        parse.accept(packageDeclarationVisitor);
        return packageDeclarationVisitor.getPackageDeclarationList().size();
    }
}
