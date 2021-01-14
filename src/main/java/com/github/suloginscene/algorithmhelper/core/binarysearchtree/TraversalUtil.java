package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;


class TraversalUtil {

    protected static <K extends Comparable<K>, V> List<BST.Node<K, V>> inOrder(BST.Node<K, V> root) {
        List<BST.Node<K, V>> nodes = new ArrayList<>();
        inOrder(root, nodes::add);
        return nodes;
    }

    protected static <K extends Comparable<K>, V> void inOrder(BST.Node<K, V> root, Consumer<BST.Node<K, V>> consumer) {
        recursiveInorder(root, consumer);
    }

    private static <K extends Comparable<K>, V> void recursiveInorder(BST.Node<K, V> node, Consumer<BST.Node<K, V>> consumer) {
        if (node == null) return;

        recursiveInorder(node.getLeft(), consumer);
        consumer.accept(node);
        recursiveInorder(node.getRight(), consumer);
    }


    protected static <K extends Comparable<K>, V> List<List<BST.Node<K, V>>> levelOrder(@NonNull BST.Node<K, V> root) {
        List<List<BST.Node<K, V>>> listList = new ArrayList<>();

        Queue<BST.Node<K, V>> queue = new LinkedList<>();
        queue.add(root);

        while (queue.stream().anyMatch(Objects::nonNull)) {
            List<BST.Node<K, V>> list = new ArrayList<>();
            while (!queue.isEmpty()) {
                list.add(queue.poll());
            }
            for (BST.Node<K, V> node : list) {
                queue.add((node != null) ? node.getLeft() : null);
                queue.add((node != null) ? node.getRight() : null);
            }
            listList.add(list);
        }

        return listList;
    }

}
