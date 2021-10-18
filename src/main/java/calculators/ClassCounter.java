package calculators;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import visitors.ClassDeclarationVisitor;
import visitors.FieldAccessVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class  ClassCounter implements ICounter{

    List<TypeDeclaration> classes = new ArrayList<>();

    @Override
    public int countItems(CompilationUnit parse) {
        ClassDeclarationVisitor classDeclarationVisitor = new ClassDeclarationVisitor();
        parse.accept(classDeclarationVisitor);
        classes = classDeclarationVisitor.getClasses();
        return classDeclarationVisitor.getClasses().size();
    }

    public List<TypeDeclaration> getClasses() {
        return classes;
    }

}
