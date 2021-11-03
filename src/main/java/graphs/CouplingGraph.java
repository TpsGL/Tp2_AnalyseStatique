package graphs;

import java.util.HashMap;
import java.util.Map;

public class CouplingGraph {

    private Map<String, Map<String, Double>> couplingClasses  = new HashMap<>();


    public void setCouplingClasses(Map<String, Map<String, Double>> map) { this.couplingClasses = map; }

    public Map<String, Map<String, Double>> getCouplingGraph() { return this.couplingClasses; }


    public void addNodeToCouplingGraph(String source, Map<String, Double> map) {
        this.couplingClasses.put(source, map);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Coupling Graph");
        builder.append("\n");

        for (String source: couplingClasses.keySet() ) {
            builder.append(source + ":\n");

            for (String destination: couplingClasses.get(source).keySet())
                builder.append("\t---> " + destination +
                        " (" + couplingClasses.get(source).get(destination) + ")\n");
            builder.append("\n");
        }

        return builder.toString();
    }

}
