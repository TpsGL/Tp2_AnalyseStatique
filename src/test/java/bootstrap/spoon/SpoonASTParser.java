package bootstrap.spoon;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAbstractInvocation;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SpoonASTParser{

    Launcher launcher;
    CtModel model;

    @BeforeEach
    void setUp() {
        launcher = new Launcher();
        launcher.addInputResource("C:\\Users\\gm_be\\IdeaProjects\\Visitor-Pattern-Implementation-Java-master");
        launcher.getEnvironment().setComplianceLevel(7);
        model = launcher.buildModel();
    }

    @Test
    void can_load_spoon_model() {
        assertNotNull(model);
    }

    @Test
    void can_get_all_classes() {

        List<CtClass> classList = model.getElements(new TypeFilter<>(CtClass.class));
        assertEquals(5, classList.size());
    }

    @Test
    void can_get_all_methods() {

        List<CtClass> classList = model.getElements(new TypeFilter<>(CtClass.class));
        assertEquals(5, classList.size());
    }

    @Test
    void can_get_methods_of_classes() {
        List<CtClass> classList = model.getElements(new TypeFilter<>(CtClass.class));
        CtClass ctClass = classList.get(0);
        Set<CtMethod> methods = ctClass.getMethods();
        CtMethod method = methods.stream().collect(Collectors.toList()).get(0);

        CtInvocation currentinvo  = (CtInvocation) method.filterChildren( (CtInvocation i) -> !i.equals("") ).list().get(0);


        CtClass calledClass = classList.stream().filter(
                classe -> classe.getSimpleName().equals(currentinvo.getTarget().getType().getSimpleName()))
                .findFirst().orElse(null);

        CtElement parent = null;
        CtClass currentClass = null;
        // get the current class
        parent = currentinvo.getParent();
        while (!(parent instanceof CtClass)) {
            parent = parent.getParent();
        }
        currentClass = (CtClass) parent;


        //  currentinvo.getTarget().getType().getSimpleName()
        assertEquals("Car", currentinvo.getExecutable().getDeclaration().getSimpleName() + currentinvo.getExecutable().getDeclaration());
    }

    @Test
    void can_get_invocations_of_method() {
        List<CtClass> classList = model.getElements(new TypeFilter<>(CtClass.class));


    }


}
