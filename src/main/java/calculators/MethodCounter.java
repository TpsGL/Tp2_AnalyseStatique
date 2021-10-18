package calculators;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import visitors.MethodDeclarationVisitor;

import java.util.ArrayList;
import java.util.List;

public class MethodCounter implements ICounter{


    List<MethodDeclaration> methodDeclarations = new ArrayList<>();

    @Override
    public int countItems(CompilationUnit parse) {
        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
        parse.accept(methodDeclarationVisitor);
        methodDeclarations.addAll(methodDeclarationVisitor.getMethods());
        return methodDeclarationVisitor.getMethods().size();
    }

    public List<MethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }


}
