package com.github.suloginscene.algorithm.helper.binarytree.bst;

import com.github.suloginscene.algorithm.helper.binarytree.Node;


public interface KvNode<N extends KvNode<N, K, V>, K extends Comparable<K>, V> extends Node<N, V>, Comparable<N> {

    K getKey();


    @Override
    default int compareTo(N another) {
        K key = getKey();
        K anotherKey = another.getKey();
        return key.compareTo(anotherKey);
    }

    default boolean isSmallerThan(N another) {
        return this.compareTo(another) < 0;
    }

    default boolean isSmallerThan(K key) {
        return getKey().compareTo(key) < 0;
    }

    default boolean isBiggerThan(N another) {
        return this.compareTo(another) > 0;
    }

    default boolean isBiggerThan(K key) {
        return getKey().compareTo(key) > 0;
    }

    default boolean isIdentifiedBy(K key) {
        return getKey().equals(key);
    }

}
