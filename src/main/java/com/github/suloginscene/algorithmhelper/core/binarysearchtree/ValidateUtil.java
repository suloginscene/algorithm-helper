package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.List;

import static java.util.stream.Collectors.toList;


class ValidateUtil {

    protected static <K extends Comparable<K>, V> void validateKey(Node<K, V> root, @NonNull K key) {
        if (!unique(root, key)) throw new IllegalArgumentException("Not unique key.");
    }

    private static <K extends Comparable<K>, V> boolean unique(Node<K, V> root, @NonNull K key) {
        if (root == null) return true;

        Node<K, V> finder = root;
        while ((finder != null) && !finder.isIdentifiedBy(key)) {
            finder = finder.isBiggerThan(key) ? finder.getLeft() : finder.getRight();
        }
        return finder == null;
    }


    protected static <K extends Comparable<K>, V> void validateBst(Node<K, V> root) {
        if (!sorted(root)) throw new IllegalArgumentException("Not sorted bst.");
    }

    private static <K extends Comparable<K>, V> boolean sorted(Node<K, V> root) {
        if (root == null) return true;

        List<Node<K, V>> nodes = TraversalUtil.inOrder(root);

        List<Node<K, V>> sorted = nodes.stream()
                .sorted(Node::compareTo)
                .collect(toList());

        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            Node<K, V> impl = nodes.get(i);
            Node<K, V> standard = sorted.get(i);
            if (!impl.equals(standard)) {
                return false;
            }
        }
        return true;
    }

}
