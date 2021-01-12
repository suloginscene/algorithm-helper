package com.github.suloginscene.algorithm.helper.binarytree.bst;

import com.github.suloginscene.algorithm.helper.binarytree.BinaryTree;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


public interface BinarySearchTree<N extends KvNode<N, K, V>, K extends Comparable<K>, V> extends BinaryTree<N, V> {

    @Override
    default void save(@NonNull N node) {
        validateKey(node.getKey());
        doSave(node);
        validateBst();
    }

    void doSave(@NonNull N node);


    @Override
    default Optional<N> find(@NonNull N node) {
        K key = node.getKey();
        return this.findNode(key);
    }

    default Optional<V> findValue(@NonNull K key) {
        N found = this.findNode(key).orElse(null);
        if (found == null) return Optional.empty();

        V value = found.getValue();
        return Optional.of(value);
    }

    Optional<N> findNode(@NonNull K key);


    @Override
    default void delete(@NonNull N node) {
        K key = node.getKey();
        this.delete(key);
    }

    default void delete(@NonNull K key) {
        doDelete(key);
        validateBst();
    }

    void doDelete(@NonNull K key);


    private void validateKey(@NonNull K key) {
        Optional<N> node = findNode(key);
        if (node.isPresent()) throw new IllegalArgumentException("Duplicated key.");
    }

    private void validateBst() {
        if (getRoot() == null) return;

        List<N> nodes = new ArrayList<>();
        inOrder(nodes::add);

        List<N> sorted = nodes.stream()
                .sorted(N::compareTo)
                .collect(toList());

        int size = nodes.size();
        for (int i = 0; i < size; i++) {
            N impl = nodes.get(i);
            N standard = sorted.get(i);
            if (!impl.equals(standard)) {
                throw new IllegalStateException("Invalid binary search tree.");
            }
        }
    }

}
