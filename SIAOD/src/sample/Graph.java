package sample;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Graph<N, W> {
    private final Map<N, Map<N, W>> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }
    public Graph(Comparator<? super N> comparator) {
        this.nodes = new TreeMap<>(comparator);
    }

    public boolean addNode(N node) {
        if (node == null || nodes.containsKey(node))
            return false;
        nodes.put(node, new HashMap<>());
        return true;
    }
    public boolean containsNode(N node) {
        return node != null && nodes.containsKey(node);
    }
    public boolean removeNode(N node) {
        if (node != null && nodes.remove(node) != null) {
            for (Map<N, W> edges : nodes.values())
                edges.remove(node);
            return true;
        }
        return false;
    }
    public boolean rename(N oldNode, N newNode) {
        if (oldNode == null || newNode == null ||
                !nodes.containsKey(oldNode) || nodes.containsKey(newNode))
            return false;
        nodes.put(newNode, nodes.remove(oldNode));
        W w;
        for (Map<N, W> edges : nodes.values())
            if ((w = edges.remove(oldNode)) != null)
                edges.put(newNode, w);
        return true;
    }

    public boolean containsEdge(N start, N end) {
        if (start == null || end == null)
            return false;
        Map<N, W> edges = nodes.get(start);
        return edges != null && edges.containsKey(end);

    }
    public W putEdge(N start, N end, W weight) {
        if (start == null || end == null || weight == null)
            return null;
        Map<N, W> edges = nodes.get(start);
        return edges == null ? null : edges.put(end, weight);
    }
    public W getEdge(N start, N end) {
        if (start == null || end == null)
            return null;
        Map<N, W> edges = nodes.get(start);
        return edges == null ? null : edges.get(end);
    }
    public W removeEdge(N start, N end) {
        if (start == null || end == null)
            return null;
        Map<N, W> edges = nodes.get(start);
        return edges == null ? null : edges.remove(end);
    }

    public Map<N, Map<N, W>> map() {
        return nodes;
    }

    public int hashCode() {
        return nodes.hashCode();
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return nodes.equals(((Graph<?, ?>) o).nodes);
    }
    public String toString() {
        if (nodes.isEmpty())
            return "[]";
        StringBuilder sb = new StringBuilder().append('[');
        for (Map.Entry<N, Map<N, W>> node : nodes.entrySet()) {
            N start = node.getKey();
            Map<N, W> edges = node.getValue();
            if (edges.isEmpty()) {
                sb.append(start).append('-').append("null").append('=').append("null").append('\n');
            } else {
                for (Map.Entry<N, W> edge : edges.entrySet())
                    sb.append(start).append('-').append(edge.getKey()).
                            append('=').append(edge.getValue()).append('\n');
            }
        }
        sb.setLength(Math.max(0, sb.length() - 1));
        return sb.append(']').toString();
    }
}