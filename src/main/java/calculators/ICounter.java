package calculators;

import org.eclipse.jdt.core.dom.CompilationUnit;

public interface ICounter {
    public int countItems(CompilationUnit parse);
}
