package com.github.suloginscene.algorithmhelper.core.graph;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;


@Slf4j
public abstract class Graph<V> {

    protected abstract Map<V, Set<V>> getVertexMap();

    protected abstract Map<Path<V>, Path<V>> getPathMap();


    public Set<V> allVertices() {
        Set<V> vertices = new HashSet<>();
        for (Map.Entry<V, Set<V>> entry : getVertexMap().entrySet()) {
            vertices.add(entry.getKey());
            vertices.addAll(entry.getValue());
        }
        return vertices;
    }

    public Set<Path<V>> allPaths() {
        return getPathMap().keySet();
    }

    public void addEdge(@NonNull V from, @NonNull V to, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight should not negative.");
        if (from == to) throw new IllegalArgumentException("Edge to itself not allowed.");

        Map<V, Set<V>> map = getVertexMap();
        if (!map.containsKey(from)) {
            map.put(from, new HashSet<>());
        }
        Set<V> neighbors = map.get(from);
        neighbors.add(to);

        Path<V> path = new Path<>(from, to, weight);
        Map<Path<V>, Path<V>> pathMap = getPathMap();
        pathMap.put(path, path);
    }

    public boolean isAdjacent(@NonNull V from, @NonNull V to) {
        Set<V> neighbors = getVertexMap().get(from);
        return neighbors != null && neighbors.contains(to);
    }

    public boolean isConnected(@NonNull V from, @NonNull V to) {
        if (isAdjacent(from, to)) return true;

        Map<V, Set<V>> map = getVertexMap();
        if (!map.containsKey(from)) return false;

        Set<V> neighbors = map.get(from);
        for (V vertex : neighbors) {
            if (isConnected(vertex, to)) return true;
        }
        return false;
    }

    public boolean hasCycle() {
        for (Map.Entry<V, Set<V>> entry : getVertexMap().entrySet()) {
            V from = entry.getKey();
            for (V to : entry.getValue()) {
                if (isConnected(to, from)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isConnectedGraph() {
        List<V> vertices = new ArrayList<>(allVertices());
        for (int i = 0; i < vertices.size() - 1; i++) {
            V from = vertices.get(i);
            for (int j = i + 1; j < vertices.size(); j++) {
                V to = vertices.get(j);
                if (!isConnected(from, to) && !isConnected(to, from)) {
                    return false;
                }
            }
        }
        return true;
    }


    public abstract void bfs(@NonNull V start, Consumer<V> consumer);

    public abstract void dfs(@NonNull V start, Consumer<V> consumer);

    public void topologicalSort(Consumer<V> consumer) {
        if (hasCycle()) throw new IllegalStateException("Graph has cycle.");
        doTopologicalSort(consumer);
    }

    protected abstract void doTopologicalSort(Consumer<V> consumer);

    public Map<V, Course<V>> shortestCourses(@NonNull V start) {
        if (!isConnectedGraph()) throw new IllegalStateException("Graph does not connected.");
        return doShortestCourses(start);
    }

    protected abstract Map<V, Course<V>> doShortestCourses(@NonNull V start);

    public Graph<V> minimumSpanningTree() {
        if (!isConnectedGraph()) throw new IllegalStateException("Graph does not connected.");
        return doMinimumSpanningTree();
    }

    protected abstract Graph<V> doMinimumSpanningTree();

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<V, Set<V>> entry : getVertexMap().entrySet()) {
            V from = entry.getKey();
            sb.append("  ").append(from).append(": ");
            for (V to : entry.getValue()) {
                sb.append(to.toString()).append(", ");
            }
            int length = sb.length();
            sb.delete(length - 2, length);
            sb.append("\n");
        }
        log.info("\n> Print vertex map\n{}", sb.toString());
    }

}
