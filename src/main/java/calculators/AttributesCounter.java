package calculators;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.internal.core.search.matching.SuperTypeNamesCollector;
import visitors.ClassDeclarationVisitor;
import visitors.FieldAccessVisitor;

public class AttributesCounter implements ICounter{

    @Override
    public int countItems(CompilationUnit parse) {
        FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor();
        parse.accept(fieldAccessVisitor);
        return fieldAccessVisitor.getFields().size();
    }
}
