import bootstrap.parsers.jdt.EclipseJDTASTParser;
import metier.graphs.coupling.CouplingGraph;
import metier.graphs.normal.Graph;
import metier.graphs.transformer.CouplingGraphTransformer;
import metier.strategy.jdt.EclipseJDTStaticCallGraphStrategy;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Consumer;

public class CouplingGraphTest {

    public static final String projectPath= "C:\\Users\\gm_be\\IdeaProjects\\Visitor-Pattern-Implementation-Java-master";

    public static final String jrePath="C:\\Program Files\\Java\\jdk1.8.0_291";


    @Test
    void can_generate_coupling_grid() {

        EclipseJDTASTParser parser = new EclipseJDTASTParser(projectPath, jrePath);

        EclipseJDTStaticCallGraphStrategy eStrategy = EclipseJDTStaticCallGraphStrategy.getInstance(parser);

        Graph graph = eStrategy.createCallGraph();

        CouplingGraphTransformer couplingGraphTransformer = new CouplingGraphTransformer(graph);

        couplingGraphTransformer.calculateMetrics();

        CouplingGraph couplingGraph = couplingGraphTransformer.getCouplingGraph();

        couplingGraph.generateProximityMatrix(eStrategy.getClasses());

        couplingGraph.printProximityMatrix();


    }
}
