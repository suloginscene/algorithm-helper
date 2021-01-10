package com.github.suloginscene.algorithm.helper.binarytree;


public interface Node<N extends Node<N, V>, V> {

    V getValue();

    N getLeft();

    N getRight();

    String toSummary();


    default boolean hasLeft() {
        return getLeft() != null;
    }

    default boolean hasRight() {
        return getRight() != null;
    }

    default boolean hasChild() {
        return hasLeft() || hasRight();
    }

    default boolean hasBothChildren() {
        return hasLeft() && hasRight();
    }

    default boolean leftIs(N node) {
        if (!hasLeft()) return false;
        return getLeft().equals(node);
    }

    default boolean rightIs(N node) {
        if (!hasRight()) return false;
        return getRight().equals(node);
    }

    default boolean isParentOf(N node) {
        return leftIs(node) || rightIs(node);
    }

}
