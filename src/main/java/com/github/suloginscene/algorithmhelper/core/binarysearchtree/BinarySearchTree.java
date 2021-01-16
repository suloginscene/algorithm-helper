package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public abstract class BinarySearchTree<K extends Comparable<K>, V> {

    public abstract Node<K, V> getRoot();


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

}
