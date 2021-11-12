package visitors.spoon;

import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class InterfaceDeclarationsCollector extends FamixCollector {

    public InterfaceDeclarationsCollector(CtModel ctModel) {
        super(ctModel);
    }

    @Override
    public void configCollector() {

    }


    public List<CtInterface> getInterfaceDeclarations() {

        return ctModel.getElements(new TypeFilter<CtInterface>(CtInterface.class));
    }
}
