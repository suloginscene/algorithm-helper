package com.github.suloginscene.algorithmhelper.core.graph;

import java.util.HashSet;
import java.util.Set;


public class SetSet<V> extends HashSet<Set<V>> {

    public Set<V> findSet(V vertex) {
        for (Set<V> set : this) {
            if (set.contains(vertex)) return set;
        }
        throw new RuntimeException("No set contains " + vertex + ".");
    }

    public void union(Set<V> a, Set<V> b) {
        remove(a);
        remove(b);
        HashSet<V> unionSet = new HashSet<>();
        unionSet.addAll(a);
        unionSet.addAll(b);
        add(unionSet);
    }

}
