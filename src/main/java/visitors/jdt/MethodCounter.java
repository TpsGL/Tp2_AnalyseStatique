package visitors.jdt;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;

public class MethodCounter {

    List<MethodDeclaration> methodDeclarations = new ArrayList<>();


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
