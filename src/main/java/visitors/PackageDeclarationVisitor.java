package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import java.util.ArrayList;
import java.util.List;

public class PackageDeclarationVisitor extends ASTVisitor {

    List<PackageDeclaration> packageDeclarations = new ArrayList<>();

    @Override
    public boolean visit(PackageDeclaration node) {

        packageDeclarations.add(node);
        return super.visit(node);
    }

    public List<PackageDeclaration> getPackageDeclarationList() {
        return packageDeclarations;
    }
}
