package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.Data;
import lombok.NonNull;


/**
 * Concrete class, providing methods for compare and child.
 * It can be extended, but I think it is enough on our BinarySearchTree.
 * BinarySearchTree is not suitable for red-black-tree, so color field is useless.
 * BinarySearchTree provide #findParentOf(Node), so parent field is unnecessary.
 *
 * @param <K> Key should be comparable and unique in tree. Node is comparable by key.
 * @param <V> Value
 */
@Data
public class Node<K extends Comparable<K>, V> implements Comparable<Node<K, V>> {

    private final K key;
    private final V value;

    private Node<K, V> left;
    private Node<K, V> right;


    public Node(@NonNull K key, @NonNull V value) {
        this.key = key;
        this.value = value;
    }


    public final boolean hasLeft() {
        return getLeft() != null;
    }

    public final boolean hasRight() {
        return getRight() != null;
    }

    public final boolean hasChild() {
        return hasLeft() || hasRight();
    }

    public final boolean hasBothChildren() {
        return hasLeft() && hasRight();
    }

    public final boolean hasOnlyChild() {
        return hasChild() && !hasBothChildren();
    }

    public final boolean leftIs(Node<K, V> node) {
        return hasLeft() && getLeft().equals(node);
    }

    public final boolean rightIs(Node<K, V> node) {
        return hasRight() && getRight().equals(node);
    }

    public final boolean isParentOf(Node<K, V> node) {
        return leftIs(node) || rightIs(node);
    }

    public final int height() {
        return MetricUtil.height(this);
    }

    @Override
    public final int compareTo(Node<K, V> another) {
        return key.compareTo(another.key);
    }

    public final boolean isSmallerThan(K key) {
        return this.key.compareTo(key) < 0;
    }

    public final boolean isSmallerThan(Node<K, V> node) {
        return compareTo(node) < 0;
    }

    public final boolean isBiggerThan(K key) {
        return this.key.compareTo(key) > 0;
    }

    public final boolean isBiggerThan(Node<K, V> node) {
        return compareTo(node) > 0;
    }

    public final boolean isIdentifiedBy(K key) {
        return this.key.equals(key);
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }

}
