package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import java.util.ArrayList;
import java.util.List;


public class AnonymousClassDeclarationVisitor extends ASTVisitor {

    List<AnonymousClassDeclaration> anonymousClassDeclarationVisitors = new ArrayList<AnonymousClassDeclaration>();

    @Override
    public boolean visit(AnonymousClassDeclaration node) {
        anonymousClassDeclarationVisitors.add(node);
        return super.visit(node);
    }

    public List<AnonymousClassDeclaration> getAnonymousClassDeclarationVisitors()
    {
        return anonymousClassDeclarationVisitors;
    }

}
