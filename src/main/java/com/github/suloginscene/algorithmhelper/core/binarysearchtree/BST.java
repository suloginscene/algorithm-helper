package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;


public abstract class BST<K extends Comparable<K>, V> {

    public abstract Node<K, V> getRoot();


    public final void save(@NonNull K key, @NonNull V value) {
        Node<K, V> node = new Node<>(key, value);
        save(node);
    }

    public final void save(@NonNull Node<K, V> node) {
        ValidateUtil.validateKey(getRoot(), node.key);
        doSave(node);
        ValidateUtil.validateBst(getRoot());
    }

    protected abstract void doSave(@NonNull Node<K, V> node);


    public final Optional<V> findValue(@NonNull K key) {
        Node<K, V> found = findNode(key).orElse(null);
        if (found == null) return Optional.empty();

        V value = found.getValue();
        return Optional.of(value);
    }

    public abstract Optional<Node<K, V>> findNode(@NonNull K key);


    public final void delete(@NonNull Node<K, V> node) {
        K key = node.getKey();
        delete(key);
    }

    public final void delete(@NonNull K key) {
        doDelete(key);
        ValidateUtil.validateBst(getRoot());
    }

    protected abstract void doDelete(@NonNull K key);


    public abstract void inOrder(Consumer<Node<K, V>> consumer);


    public final int size() {
        return MetricUtil.size(getRoot());
    }

    public final int height() {
        return MetricUtil.height(getRoot());
    }

    public final void printByKey() {
        print(n -> n.key.toString());
    }

    public final void printByValue() {
        print(n -> n.value.toString());
    }

    public final void print(Function<Node<K, V>, String> toStringFunction) {
        PrintUtil.print(getRoot(), toStringFunction);
    }

    public final void printPaths() {
        PrintUtil.printPaths(getRoot());
    }


    public static final class Node<K extends Comparable<K>, V> implements Comparable<Node<K, V>> {

        private final K key;
        private final V value;

        private Node<K, V> left;
        private Node<K, V> right;


        public Node(@NonNull K key, @NonNull V value) {
            this.key = key;
            this.value = value;
        }


        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final Node<K, V> getLeft() {
            return left;
        }

        public final void setLeft(Node<K, V> left) {
            this.left = left;
        }

        public final Node<K, V> getRight() {
            return right;
        }

        public final void setRight(Node<K, V> right) {
            this.right = right;
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
            if (!hasLeft()) return false;
            return getLeft().equals(node);
        }

        public final boolean rightIs(Node<K, V> node) {
            if (!hasRight()) return false;
            return getRight().equals(node);
        }

        public final boolean isParentOf(Node<K, V> node) {
            return leftIs(node) || rightIs(node);
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
        public final String toString() {
            return "Node{" + "key=" + key + ", value=" + value + '}';
        }

    }

}
