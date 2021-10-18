package calculators;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTContext {

    private ICounter iCounter;

    ASTContext() { }

    public void setCounter(ICounter iCounter){
        this.iCounter = iCounter;
    }

    public int executeCounter(CompilationUnit compilationUnit){
        return iCounter.countItems(compilationUnit);
    }


}

