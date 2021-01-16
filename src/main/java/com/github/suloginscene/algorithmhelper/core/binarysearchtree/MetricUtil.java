package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;


class MetricUtil {

    protected static <K extends Comparable<K>, V> int size(Node<K, V> root) {
        List<Node<K, V>> nodes = TraversalUtil.inOrder(root);
        return nodes.size();
    }


    protected static <K extends Comparable<K>, V> int height(Node<K, V> root) {
        List<Integer> heights = new ArrayList<>();
        inOrderToHeight(root, 1, heights::add);
        return heights.stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    private static <K extends Comparable<K>, V> void inOrderToHeight(Node<K, V> node, int height, Consumer<Integer> consumer) {
        if (node == null) return;

        inOrderToHeight(node.getLeft(), height + 1, consumer);
        consumer.accept(height);
        inOrderToHeight(node.getRight(), height + 1, consumer);
    }


    protected static <K extends Comparable<K>, V> boolean isHigherThan(Node<K, V> root, int standard) {
        Consumer<Integer> exceptionThrower = height -> {
            if (height > standard) throw new IllegalStateException();
        };

        try {
            inOrderToHeight(root, 1, exceptionThrower);
        } catch (IllegalStateException e) {
            return true;
        }
        return false;
    }

}
