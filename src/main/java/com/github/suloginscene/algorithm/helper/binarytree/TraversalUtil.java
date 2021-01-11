package com.github.suloginscene.algorithm.helper.binarytree;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;


class TraversalUtil {

    protected static <N extends Node<N, V>, V> List<N> inOrder(@NonNull N root) {
        List<N> nodes = new ArrayList<>();
        inOrder(root, nodes::add);
        return nodes;
    }

    protected static <N extends Node<N, V>, V> void inOrder(@NonNull N root, Consumer<N> consumer) {
        recursiveInorder(root, consumer);
    }

    private static <N extends Node<N, V>, V> void recursiveInorder(N node, Consumer<N> consumer) {
        if (node == null) return;

        recursiveInorder(node.getLeft(), consumer);
        consumer.accept(node);
        recursiveInorder(node.getRight(), consumer);
    }


    protected static <N extends Node<N, V>, V> List<List<N>> levelOrder(@NonNull N root) {
        List<List<N>> listList = new ArrayList<>();

        Queue<N> queue = new LinkedList<>();
        queue.add(root);

        while (queue.stream().anyMatch(Objects::nonNull)) {
            List<N> list = new ArrayList<>();
            while (!queue.isEmpty()) {
                list.add(queue.poll());
            }
            for (N node : list) {
                queue.add((node != null) ? node.getLeft() : null);
                queue.add((node != null) ? node.getRight() : null);
            }
            listList.add(list);
        }

        return listList;
    }


    protected static <N extends Node<N, V>, V> int findHeight(@NonNull N root) {
        List<Integer> heights = new ArrayList<>();
        inOrderToFindHeight(root, heights::add);
        return heights.stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    private static <N extends Node<N, V>, V> void inOrderToFindHeight(@NonNull N root, Consumer<Integer> consumer) {
        recursiveInOrderToFindHeight(root, 1, consumer);
    }

    private static <N extends Node<N, V>, V> void recursiveInOrderToFindHeight(N node, int height, Consumer<Integer> consumer) {
        if (node == null) return;

        recursiveInOrderToFindHeight(node.getLeft(), height + 1, consumer);
        consumer.accept(height);
        recursiveInOrderToFindHeight(node.getRight(), height + 1, consumer);
    }

}
