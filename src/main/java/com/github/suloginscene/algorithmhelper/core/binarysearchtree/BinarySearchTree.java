package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.Consumer;


@Getter
public abstract class BinarySearchTree<K extends Comparable<K>, V> {

    protected Node<K, V> root;


    public final void save(@NonNull K key, @NonNull V value) {
        Node<K, V> node = new Node<>(key, value);
        save(node);
    }

    public final void save(@NonNull Node<K, V> node) {
        ValidateUtil.validateKey(root, node.key);
        doSave(node);
        ValidateUtil.validateBst(root);
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
        ValidateUtil.validateBst(root);
    }

    protected abstract void doDelete(@NonNull K key);


    public abstract void inOrder(Consumer<Node<K, V>> consumer);


    public final int size() {
        return MetricUtil.size(root);
    }

    public final int height() {
        return MetricUtil.height(root);
    }

    public final void print() {
        PrintUtil.print(root);
    }


    @Getter
    public static class Node<K extends Comparable<K>, V> implements Comparable<Node<K, V>> {

        private final K key;
        private final V value;

        private Node<K, V> left;
        private Node<K, V> right;


        public Node(@NonNull K key, @NonNull V value) {
            this.key = key;
            this.value = value;
        }


        protected String toSummary() {
            return key.toString();
        }


        public boolean hasLeft() {
            return getLeft() != null;
        }

        public boolean hasRight() {
            return getRight() != null;
        }

        public boolean hasChild() {
            return hasLeft() || hasRight();
        }

        public boolean hasBothChildren() {
            return hasLeft() && hasRight();
        }

        public boolean leftIs(Node<K, V> node) {
            if (!hasLeft()) return false;
            return getLeft().equals(node);
        }

        public boolean rightIs(Node<K, V> node) {
            if (!hasRight()) return false;
            return getRight().equals(node);
        }

        public boolean isParentOf(Node<K, V> node) {
            return leftIs(node) || rightIs(node);
        }


        @Override
        public int compareTo(Node<K, V> another) {
            return key.compareTo(another.key);
        }

        public boolean isSmallerThan(K key) {
            return this.key.compareTo(key) < 0;
        }

        public boolean isBiggerThan(K key) {
            return this.key.compareTo(key) > 0;
        }

        public boolean isIdentifiedBy(K key) {
            return this.key.equals(key);
        }

    }

}
