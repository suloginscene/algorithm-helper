package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;


class MetricUtil {

    protected static <K extends Comparable<K>, V> int size(BST.Node<K, V> root) {
        List<BST.Node<K, V>> nodes = TraversalUtil.inOrder(root);
        return nodes.size();
    }


    protected static <K extends Comparable<K>, V> int height(BST.Node<K, V> root) {
        List<Integer> heights = new ArrayList<>();
        inOrderToHeight(root, heights::add);
        return heights.stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    private static <K extends Comparable<K>, V> void inOrderToHeight(BST.Node<K, V> root, Consumer<Integer> consumer) {
        recursiveInOrderToHeight(root, 1, consumer);
    }

    private static <K extends Comparable<K>, V> void recursiveInOrderToHeight(BST.Node<K, V> node, int height, Consumer<Integer> consumer) {
        if (node == null) return;

        recursiveInOrderToHeight(node.getLeft(), height + 1, consumer);
        consumer.accept(height);
        recursiveInOrderToHeight(node.getRight(), height + 1, consumer);
    }


    protected static <K extends Comparable<K>, V> boolean isHigherThan(BST.Node<K, V> root, int standard) {
        Consumer<Integer> exceptionThrower = height -> {
            if (height > standard) throw new IllegalStateException();
        };

        try {
            inOrderToHeight(root, exceptionThrower);
        } catch (IllegalStateException e) {
            return true;
        }
        return false;
    }

}
