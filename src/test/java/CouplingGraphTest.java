
import bootstrap.parsers.jdt.EclipseJDTASTParser;
import bootstrap.parsers.spoon.SpoonASTParser;
import metier.graphs.coupling.CouplingGraph;
import metier.graphs.normal.Graph;
import metier.graphs.transformer.CouplingGraphTransformer;
import metier.strategy.jdt.EclipseJDTStaticCallGraphStrategy;
import metier.strategy.spoon.SpoonStaticCallGraphStrategy;
import org.junit.jupiter.api.Test;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.IOException;
import java.util.stream.Collectors;


public class CouplingGraphTest {

    public static final String projectPath= "C:\\Users\\gm_be\\IdeaProjects\\Visitor-Pattern-Implementation-Java-master";

    //public static final String projectPath = "C:\\Users\\gm_be\\IdeaProjects\\chain_of_responsibility";

    public static final String jrePath="C:\\Program Files\\Java\\jdk1.8.0_291";


    @Test
    void can_generate_coupling_grid() throws IOException {

        //bootstrap.parsers.spoon.SpoonASTParser
        //        parser = new bootstrap.parsers.spoon.SpoonASTParser(projectPath, jrePath);

        SpoonASTParser parser = new SpoonASTParser(projectPath, jrePath);


        SpoonStaticCallGraphStrategy eStrategy = SpoonStaticCallGraphStrategy.getInstance(parser);

        //EclipseJDTASTParser eclipseJDTASTParser = new EclipseJDTASTParser(projectPath, jrePath);

        //EclipseJDTStaticCallGraphStrategy eStrategy = EclipseJDTStaticCallGraphStrategy.getInstance(eclipseJDTASTParser);

        Graph graph = eStrategy.createCallGraph();

        CouplingGraphTransformer couplingGraphTransformer = new CouplingGraphTransformer(graph);

        couplingGraphTransformer.calculateMetrics();

        CouplingGraph couplingGraph = couplingGraphTransformer.getCouplingGraph();

        couplingGraph.generateProximityMatrix(eStrategy.getClasses());

        couplingGraph.printProximityMatrix();


    }
}
