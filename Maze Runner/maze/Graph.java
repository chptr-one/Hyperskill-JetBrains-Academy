package maze;

import java.io.*;
import java.util.*;

public class Graph<T> implements Serializable {
    Map<T, List<T>> adj;
    private int numberOfVertices;
    private long numberOfEdges;

    public Graph() {
        this.numberOfVertices = 0;
        numberOfEdges = 0L;
        adj = new HashMap<>();
    }

    public void addVertex(T v) {
        if (v == null) throw new NullPointerException();
        if (!adj.containsKey(v)) {
            adj.put(v, null);
            numberOfVertices++;
        }
    }

    public void addEdge(T v1, T v2) {
        if (v1 == null || v2 == null) throw new NullPointerException();
        if (adj.containsKey(v1) && adj.containsKey(v2)) {
            adj.computeIfAbsent(v1, k -> new LinkedList<>());
            adj.computeIfAbsent(v2, k -> new LinkedList<>());
            adj.get(v1).add(v2);
            adj.get(v2).add(v1);
            numberOfEdges++;
        } else if (!adj.containsKey(v1)) {
            throw new IllegalArgumentException("Unknown vertex \"" + v1 + "\"");
        } else if (!adj.containsKey(v2)) {
            throw new IllegalArgumentException("Unknown vertex \"" + v2 + "\"");
        }
    }

    public Set<T> getVertices() {
        return adj.keySet();
    }

    public List<T> adjacency(T v) {
        if (v == null) throw new NullPointerException();
        return adj.get(v);
    }

    public boolean containsVertex(T v) {
        return adj.containsKey(v);
    }

    @Override
    public String toString() {
        return "Graph" + adj +
                ", Vertices=" + numberOfVertices +
                ", Edges=" + numberOfEdges +
                '}';
    }
}
