package metier.strategy;

import metier.graphs.normal.Graph;

import java.io.IOException;

public interface StaticCallGraphCreatorStrategy {
    public Graph createCallGraph() throws IOException;
}
