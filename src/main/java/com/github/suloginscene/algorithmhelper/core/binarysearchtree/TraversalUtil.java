package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;


class TraversalUtil {

    protected static <K extends Comparable<K>, V> List<Node<K, V>> inOrder(Node<K, V> root) {
        List<Node<K, V>> nodes = new ArrayList<>();
        recursiveInorder(root, nodes::add);
        return nodes;
    }

    private static <K extends Comparable<K>, V> void recursiveInorder(Node<K, V> node, Consumer<Node<K, V>> consumer) {
        if (node == null) return;

        recursiveInorder(node.getLeft(), consumer);
        consumer.accept(node);
        recursiveInorder(node.getRight(), consumer);
    }


    protected static <K extends Comparable<K>, V> List<List<Node<K, V>>> levelOrder(Node<K, V> root) {
        List<List<Node<K, V>>> listList = new ArrayList<>();
        if (root == null) return listList;

        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.add(root);

        while (queue.stream().anyMatch(Objects::nonNull)) {
            List<Node<K, V>> list = new ArrayList<>();
            while (!queue.isEmpty()) {
                list.add(queue.poll());
            }
            for (Node<K, V> node : list) {
                queue.add((node != null) ? node.getLeft() : null);
                queue.add((node != null) ? node.getRight() : null);
            }
            listList.add(list);
        }

        return listList;
    }

}
