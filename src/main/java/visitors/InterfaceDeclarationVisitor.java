package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import java.util.ArrayList;
import java.util.List;

public class InterfaceDeclarationVisitor extends ASTVisitor {

    List<TypeDeclaration> interfaces = new ArrayList<TypeDeclaration>();

    @Override
    public boolean visit(TypeDeclaration node) {
        if( node.isInterface() ) {
            interfaces.add(node);
        }
        return super.visit(node);
    }

    public List<TypeDeclaration> getInterfaces() { return interfaces; }
}
