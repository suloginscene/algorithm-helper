package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public abstract class BinarySearchTree<K extends Comparable<K>, V> {

    public abstract Node<K, V> getRoot();

    public abstract void setRoot(Node<K, V> node);

    public void save(@NonNull K key, @NonNull V value) {
        ValidateUtil.validateKey(getRoot(), key);
        doSave(new Node<>(key, value));
        ValidateUtil.validateBst(getRoot());
    }

    protected abstract void doSave(@NonNull Node<K, V> node);


    public Optional<V> findValue(@NonNull K key) {
        Node<K, V> found = findNode(key).orElse(null);
        if (found == null) return Optional.empty();

        V value = found.getValue();
        return Optional.of(value);
    }

    public abstract Optional<Node<K, V>> findNode(@NonNull K key);


    public void delete(@NonNull K key) {
        doDelete(key);
        ValidateUtil.validateBst(getRoot());
    }

    protected abstract void doDelete(@NonNull K key);


    public abstract void inOrder(Consumer<Node<K, V>> consumer);


    public int size() {
        return MetricUtil.size(getRoot());
    }

    public int height() {
        return MetricUtil.height(getRoot());
    }

    public void print(Function<Node<K, V>, String> toStringFunction) {
        PrintUtil.print(getRoot(), toStringFunction);
    }

    public void printPaths() {
        PrintUtil.printPaths(getRoot());
    }

    public Optional<Node<K, V>> findParentOf(@NonNull Node<K, V> node) {
        Node<K, V> root = getRoot();
        if (node == root) return Optional.empty();

        Node<K, V> pointer = root;
        while (!pointer.isParentOf(node)) {
            pointer = (pointer.isBiggerThan(node)) ? pointer.getLeft() : pointer.getRight();
        }
        return Optional.of(pointer);
    }

    public Optional<Node<K, V>> findSuccessorOf(@NonNull Node<K, V> node) {
        if (!node.hasRight()) return Optional.empty();

        Node<K, V> successor = node.getRight();
        while (successor.hasLeft()) {
            successor = successor.getLeft();
        }
        return Optional.of(successor);
    }

    public void rotateLeft(@NonNull Node<K, V> pivot) {
        Node<K, V> subs = pivot.getRight();
        if (subs == null) throw new IllegalArgumentException("Not rotatable.");
        transplant(pivot, subs);
        pivot.setRight(subs.getLeft());
        subs.setLeft(pivot);
    }

    public void rotateRight(@NonNull Node<K, V> pivot) {
        Node<K, V> subs = pivot.getLeft();
        if (subs == null) throw new IllegalArgumentException("Not rotatable.");
        transplant(pivot, subs);
        pivot.setLeft(subs.getRight());
        subs.setRight(pivot);
    }

    public void transplant(@NonNull Node<K, V> position, Node<K, V> node) {
        if (position == getRoot()) {
            setRoot(node);
        } else {
            Node<K, V> parent = findParentOf(position).orElseThrow();
            if (parent.leftIs(position)) parent.setLeft(node);
            else parent.setRight(node);
        }
    }

}
