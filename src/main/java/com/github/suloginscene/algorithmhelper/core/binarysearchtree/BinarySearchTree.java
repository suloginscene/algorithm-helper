package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Core abstract class to extend, providing utility methods.
 * It can be used for making default-bst or avl-tree.
 * But it is not suitable for red-black-tree, because nil node is not supported.
 *
 * @param <K> Key for node
 * @param <V> Value for node
 */
public abstract class BinarySearchTree<K extends Comparable<K>, V> {

    /**
     * Root node should exist on field.
     *
     * @return root node on field.
     */
    protected abstract Node<K, V> getRoot();

    /**
     * Set root node on field.
     */
    protected abstract void setRoot(Node<K, V> node);


    /**
     * Public interface validating key and status.
     * This is template method wrapping abstract method doSave().
     * Before doSave(), it check key is unique.
     * After doSave(), it check bst is sorted.
     */
    public void save(@NonNull K key, @NonNull V value) {
        ValidateUtil.validateKey(getRoot(), key);
        doSave(new Node<>(key, value));
        ValidateUtil.validateBst(getRoot());
    }

    /**
     * Abstract method to implement.
     */
    protected abstract void doSave(@NonNull Node<K, V> node);


    /**
     * Public interface just using findNode().
     */
    public Optional<V> findValue(@NonNull K key) {
        Node<K, V> found = findNode(key).orElse(null);
        if (found == null) return Optional.empty();

        V value = found.getValue();
        return Optional.of(value);
    }

    /**
     * Abstract method to implement as Public interface.
     */
    public abstract Optional<Node<K, V>> findNode(@NonNull K key);


    /**
     * Public interface validating status.
     * After doDelete(), it check bst is sorted.
     */
    public void delete(@NonNull K key) {
        doDelete(key);
        ValidateUtil.validateBst(getRoot());
    }

    /**
     * Abstract method to implement.
     */
    protected abstract void doDelete(@NonNull K key);


    /**
     * Abstract method to implement.
     */
    public abstract void inOrder(Consumer<Node<K, V>> consumer);


    /**
     * @return size is count of all node.
     */
    public int size() {
        return MetricUtil.size(getRoot());
    }

    /**
     * @return height of root node is 1.
     */
    public int height() {
        return MetricUtil.height(getRoot());
    }

    /**
     * Print in tree shape.
     * @param toStringFunction function converting node to string, like Node::toString.
     */
    public void print(Function<Node<K, V>, String> toStringFunction) {
        PrintUtil.print(getRoot(), toStringFunction);
    }

    /**
     * It prints all paths from root to leaf.
     */
    public void printPaths() {
        PrintUtil.printPaths(getRoot());
    }

    protected Optional<Node<K, V>> findParentOf(@NonNull Node<K, V> node) {
        Node<K, V> root = getRoot();
        if (node == root) return Optional.empty();

        Node<K, V> pointer = root;
        while (!pointer.isParentOf(node)) {
            pointer = (pointer.isBiggerThan(node)) ? pointer.getLeft() : pointer.getRight();
        }
        return Optional.of(pointer);
    }

    protected Optional<Node<K, V>> findSuccessorOf(@NonNull Node<K, V> node) {
        if (!node.hasRight()) return Optional.empty();

        Node<K, V> successor = node.getRight();
        while (successor.hasLeft()) {
            successor = successor.getLeft();
        }
        return Optional.of(successor);
    }

    protected void rotateLeft(@NonNull Node<K, V> pivot) {
        Node<K, V> subs = pivot.getRight();
        if (subs == null) throw new IllegalArgumentException("Not rotatable.");
        transplant(pivot, subs);
        pivot.setRight(subs.getLeft());
        subs.setLeft(pivot);
    }

    protected void rotateRight(@NonNull Node<K, V> pivot) {
        Node<K, V> subs = pivot.getLeft();
        if (subs == null) throw new IllegalArgumentException("Not rotatable.");
        transplant(pivot, subs);
        pivot.setLeft(subs.getRight());
        subs.setRight(pivot);
    }

    protected void transplant(@NonNull Node<K, V> position, Node<K, V> node) {
        if (position == getRoot()) {
            setRoot(node);
        } else {
            Node<K, V> parent = findParentOf(position).orElseThrow();
            if (parent.leftIs(position)) parent.setLeft(node);
            else parent.setRight(node);
        }
    }

}
