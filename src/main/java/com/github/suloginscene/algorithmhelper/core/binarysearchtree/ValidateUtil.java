package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


class ValidateUtil {

    protected static <K extends Comparable<K>, V> void validateKey(BST.Node<K, V> root, @NonNull K key) {
        Optional<BST.Node<K, V>> node = findNode(root, key);
        if (node.isPresent()) throw new IllegalArgumentException("Duplicated key.");
    }

    private static <K extends Comparable<K>, V> Optional<BST.Node<K, V>> findNode(BST.Node<K, V> root, @NonNull K key) {
        if (root == null) return Optional.empty();

        BST.Node<K, V> finder = root;
        while ((finder != null) && !finder.isIdentifiedBy(key)) {
            finder = finder.isBiggerThan(key) ? finder.getLeft() : finder.getRight();
        }
        return Optional.ofNullable(finder);
    }


    protected static <K extends Comparable<K>, V> void validateBst(BST.Node<K, V> root) {
        if (root == null) return;

        List<BST.Node<K, V>> nodes = TraversalUtil.inOrder(root);

        List<BST.Node<K,V>> sorted = nodes.stream()
                .sorted(BST.Node::compareTo)
                .collect(toList());

        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            BST.Node<K,V> impl = nodes.get(i);
            BST.Node<K,V> standard = sorted.get(i);
            if (!impl.equals(standard)) {
                throw new IllegalStateException("Invalid binary search tree.");
            }
        }
    }

}
