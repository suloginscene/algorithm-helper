package com.github.suloginscene.algorithm.helper.binarytree;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


public interface BinaryTree<N extends Node<N, V>, V> {

    N getRoot();

    void save(@NonNull N node);

    Optional<N> find(@NonNull N node);

    void delete(@NonNull N node);


    default int size() {
        List<N> nodes = new ArrayList<>();
        inOrder(nodes::add);
        return nodes.size();
    }

    default int height() {
        return TraversalUtil.height(getRoot());
    }

    default void inOrder(Consumer<N> consumer) {
        TraversalUtil.inOrder(getRoot(), consumer);
    }

    default void inOrder(N root, Consumer<N> consumer) {
        TraversalUtil.inOrder(root, consumer);
    }


    default void print() {
        PrintUtil.print(getRoot());
    }

}
