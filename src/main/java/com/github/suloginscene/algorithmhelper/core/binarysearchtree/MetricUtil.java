package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;


class MetricUtil {

    protected static <K extends Comparable<K>, V> int size(BinarySearchTree.Node<K, V> root) {
        List<BinarySearchTree.Node<K, V>> nodes = TraversalUtil.inOrder(root);
        return nodes.size();
    }


    protected static <K extends Comparable<K>, V> int height(@NonNull BinarySearchTree.Node<K, V> root) {
        List<Integer> heights = new ArrayList<>();
        inOrderToHeight(root, heights::add);
        return heights.stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    private static <K extends Comparable<K>, V> void inOrderToHeight(@NonNull BinarySearchTree.Node<K, V> root, Consumer<Integer> consumer) {
        recursiveInOrderToHeight(root, 1, consumer);
    }

    private static <K extends Comparable<K>, V> void recursiveInOrderToHeight(BinarySearchTree.Node<K, V> node, int height, Consumer<Integer> consumer) {
        if (node == null) return;

        recursiveInOrderToHeight(node.getLeft(), height + 1, consumer);
        consumer.accept(height);
        recursiveInOrderToHeight(node.getRight(), height + 1, consumer);
    }

}
