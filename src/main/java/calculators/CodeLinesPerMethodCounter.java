package calculators;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import visitors.MethodDeclarationVisitor;

import java.util.Map;

public class CodeLinesPerMethodCounter implements ICounter {



    @Override
    public int countItems(CompilationUnit cu) {
        MethodDeclarationVisitor visitor = new MethodDeclarationVisitor();
        cu.accept(visitor);
        int linesCodeNumberPerMethod = 0;

        for(MethodDeclaration methodDeclaration : visitor.getMethods()) {
            Block block = methodDeclaration.getBody();
            if ( block == null ) {
                continue;
            }
            linesCodeNumberPerMethod += block.statements().size();
        }
        return linesCodeNumberPerMethod;
    }
}
