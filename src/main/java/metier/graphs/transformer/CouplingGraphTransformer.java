package metier.graphs.transformer;

import metier.graphs.coupling.CouplingGraph;
import metier.graphs.normal.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouplingGraphTransformer {

    private Graph graph;

    private CouplingGraph couplingGraph = new CouplingGraph();

    public CouplingGraphTransformer(Graph graph) { this.graph = graph; }

    public CouplingGraph getCouplingGraph() {
        return couplingGraph;
    }

    private void transform() {

        Map<String, Map<String, Map<String, String>>> sourceClassesInvocations = graph.getSourceClassesInvocations();

        for (String source: sourceClassesInvocations.keySet()) {

            Map<String, Double> map = new HashMap();

            List<String> list = new ArrayList<>();

            for (String destination: sourceClassesInvocations.get(source).keySet()) {

                list.addAll(sourceClassesInvocations.get(source).get(destination).values());

                for (String classItem: sourceClassesInvocations.get(source).get(destination).values()) {

                    double ratio = (double) (map.get(classItem)!=null ? map.get(classItem) + 1 : 1);

                    map.put(classItem, ratio);
                }
            }

            couplingGraph.addNodeToCouplingGraph(source, map);

            list.clear();
        }



    }

    public void calculateMetrics() {

        transform();

        double nbInvocation = (double) graph.getNbInvocations();

        for (String source : couplingGraph.getCouplingGraph().keySet()) {

            for (String destination: couplingGraph.getCouplingGraph().get(source).keySet()) {

                double occ = couplingGraph.getCouplingGraph().get(source).get(destination);

                couplingGraph.getCouplingGraph().get(source).put(destination, occ/nbInvocation);
            }
        }

        System.out.println(couplingGraph.toString());
    }
}
