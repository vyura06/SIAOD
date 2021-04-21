package sample;

import java.util.Map;
import java.util.function.Function;

public class LinksCounter {
    public int in, out;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinksCounter linksCounter = (LinksCounter) o;
        return in == linksCounter.in && out == linksCounter.out;
    }
    public int hashCode() {
        return in ^ out;
    }
    public String toString() {
        return "[in=" + in + ", out=" + out + ']';
    }

    public static <N, W> void count(Graph<N, W> graph, Map<N, LinksCounter> out) {
        if (graph.map().isEmpty())
            return;
        Function<N, LinksCounter> creator = n -> new LinksCounter();
        for (Map.Entry<N, Map<N, W>> node : graph.map().entrySet()) {
            for (N edge : node.getValue().keySet()) {
                out.computeIfAbsent(node.getKey(), creator).out++;
                out.computeIfAbsent(edge, creator).in++;
            }
        }
    }
}