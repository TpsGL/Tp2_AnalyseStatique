package visitors.spoon;


import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.List;

public class ClassDeclarationsCollector extends FamixCollector {


    public ClassDeclarationsCollector(CtModel ctModel) {
        super(ctModel);
    }

    public List<CtClass> getClassDeclarations() {
       return ctModel.getElements(new TypeFilter<CtClass>(CtClass.class));
    }

    @Override
    public void configCollector() {

    }
}
