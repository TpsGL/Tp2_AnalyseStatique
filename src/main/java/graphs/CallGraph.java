package graphs;

import java.util.*;
import java.util.stream.LongStream;

public class CallGraph {

    /**
     * Methods Set of methods
     */
    private Set<String> methods = new HashSet<>();

    /**
     * Map (source method, [invoked methods, Time of execution])
     */
    public Map<String, Map<String, Map<String, String>>> invocations = new HashMap<>();

    public Set<String> getMethods() { return methods; }

    public long getNbMethods() {
        return methods.size();
    }

    public long getNbInvocations() {
        return invocations.keySet()
                .stream()
                .map(source -> invocations.get(source))
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(Map::keySet)
                .mapToLong(Collection::size)
                .sum();
    }

    // Map<_class_x::method, Map<_function_class_x_with_parameters::method, Map<Invoked_method_with_parameters::method, from::class > > >
    //

    public Map<String, Map<String, Map<String, String>>> getInvocations() {
        return invocations;
    }










}
