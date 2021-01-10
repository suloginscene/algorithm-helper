package com.github.suloginscene.algorithm.helper.binarytree;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;


class TraversalUtil {

    protected static <N extends Node<N, V>, V> List<N> inOrder(@NonNull N root) {
        List<N> nodes = new ArrayList<>();
        recursiveInorder(root, nodes::add);
        return nodes;
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

}
