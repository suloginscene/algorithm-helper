package com.github.suloginscene.algorithm.helper.binarytree;

import lombok.NonNull;

import java.util.Optional;


public interface BinaryTree<N extends Node<N, V>, V> {

    N getRoot();

    void save(@NonNull N node);

    Optional<N> find(@NonNull N node);

    void delete(@NonNull N node);


    default void print() {
        PrintUtil.print(getRoot());
    }

}
