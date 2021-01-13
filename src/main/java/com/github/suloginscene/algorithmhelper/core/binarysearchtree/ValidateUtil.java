package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


class ValidateUtil {

    protected static <K extends Comparable<K>, V> void validateKey(BinarySearchTree.Node<K, V> root, @NonNull K key) {
        Optional<BinarySearchTree.Node<K, V>> node = findNode(root, key);
        if (node.isPresent()) throw new IllegalArgumentException("Duplicated key.");
    }

    private static <K extends Comparable<K>, V> Optional<BinarySearchTree.Node<K, V>> findNode(BinarySearchTree.Node<K, V> root, @NonNull K key) {
        if (root == null) return Optional.empty();

        BinarySearchTree.Node<K, V> finder = root;
        while ((finder != null) && !finder.isIdentifiedBy(key)) {
            finder = finder.isBiggerThan(key) ? finder.getLeft() : finder.getRight();
        }
        return Optional.ofNullable(finder);
    }


    protected static <K extends Comparable<K>, V> void validateBst(BinarySearchTree.Node<K, V> root) {
        if (root == null) return;

        List<BinarySearchTree.Node<K, V>> nodes = TraversalUtil.inOrder(root);

        List<BinarySearchTree.Node<K,V>> sorted = nodes.stream()
                .sorted(BinarySearchTree.Node::compareTo)
                .collect(toList());

        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            BinarySearchTree.Node<K,V> impl = nodes.get(i);
            BinarySearchTree.Node<K,V> standard = sorted.get(i);
            if (!impl.equals(standard)) {
                throw new IllegalStateException("Invalid binary search tree.");
            }
        }
    }

}
