package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import java.util.ArrayList;
import java.util.List;


class PathUtil {

    protected static <K extends Comparable<K>, V> List<List<BST.Node<K, V>>> pathsToNil(BST.Node<K, V> root) {
        List<List<BST.Node<K, V>>> paths = new ArrayList<>();
        List<BST.Node<K, V>> path = new ArrayList<>();
        recursivePathsToNil(root, paths, path);
        return paths;
    }

    private static <K extends Comparable<K>, V> void recursivePathsToNil(BST.Node<K, V> node, List<List<BST.Node<K, V>>> paths, List<BST.Node<K, V>> path) {

        path.add(node);

        if (!node.hasChild()) {
            paths.add(new ArrayList<>(path));
            path.remove(node);
            return;
        }

        if (node.hasOnlyChild()) {
            paths.add(new ArrayList<>(path));
        }

        if (node.hasLeft()) {
            recursivePathsToNil(node.getLeft(), paths, path);
            path.remove(node.getLeft());
        }

        if (node.hasRight()) {
            recursivePathsToNil(node.getRight(), paths, path);
            path.remove(node.getRight());
        }

    }


    protected static <K extends Comparable<K>, V> List<BST.Node<K, V>> pathToNode(BST.Node<K, V> root, K key) {
        ArrayList<BST.Node<K, V>> path = new ArrayList<>();
        if (root == null) return path;

        BST.Node<K, V> finder = root;
        while ((finder != null) && !finder.isIdentifiedBy(key)) {
            path.add(finder);
            finder = finder.isBiggerThan(key) ? finder.getLeft() : finder.getRight();
        }
        path.add(finder);
        return path;
    }

}
