package visitors;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldAccessVisitor extends ASTVisitor {

    List<FieldDeclaration> fields = new ArrayList<>();

    public boolean visit(TypeDeclaration node) {

        fields.addAll(Arrays.asList(node.getFields()));
        return super.visit(node);
    }

    public List<FieldDeclaration> getFields() {
        return fields;
    }
}
