package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;


@Slf4j
class PrintUtil {

    /**
     * [ row > element > content > string ]
     * Row have many elements.
     * Element wrap content in parenthesis and has margin.
     * Content normalize length of summary.
     * summary from toStringFunction.
     */
    protected static <K extends Comparable<K>, V> void print(@NonNull BST.Node<K, V> root, Function<BST.Node<K, V>, String> toStringFunction) {
        if (MetricUtil.isHigherThan(root, 7)) {
            log.warn("\n> Too big to print (max height: 7)\n");
            return;
        }

        StringBuilder sb = new StringBuilder();
        List<List<BST.Node<K, V>>> levelOrdered = TraversalUtil.levelOrder(root);

        int contentDigit = DigitUtil.calculateContentDigit(root, toStringFunction);
        int elementDigit = DigitUtil.calculateElementDigit(contentDigit);
        int rowDigit = DigitUtil.calculateRowDigit(levelOrdered, elementDigit);

        for (List<BST.Node<K, V>> level : levelOrdered) {
            int marginDigit = DigitUtil.calculateMarginDigit(level, elementDigit, rowDigit);

            for (BST.Node<K, V> node : level) {
                String summary = ConvertUtil.nodeToSummary(node, toStringFunction);
                String content = ConvertUtil.summaryToContent(summary, contentDigit);
                String element = ConvertUtil.contentToElement(content, marginDigit);
                sb.append(element);
            }
            sb.append("\n");
        }

        log.info("\n> Print Tree\n" + sb.toString());
    }


    protected static <K extends Comparable<K>, V> void printPaths(@NonNull BST.Node<K, V> root) {
        if (MetricUtil.isHigherThan(root, 5)) {
            log.warn("\n> Too big to print paths (max height: 5)\n");
            return;
        }

        Function<BST.Node<K, V>, String> toKeyFunction = n -> n.getKey().toString();
        int contentDigit = DigitUtil.calculateContentDigit(root, toKeyFunction);

        StringBuilder sb = new StringBuilder();
        sb.append("\n> Print paths\n");

        List<List<BST.Node<K, V>>> lists = PathUtil.pathsToNil(root);
        for (List<BST.Node<K, V>> list : lists) {
            sb.append("  ");
            for (BST.Node<K, V> node : list) {
                String summary = ConvertUtil.nodeToSummary(node, toKeyFunction);
                String content = ConvertUtil.summaryToContent(summary, contentDigit);
                sb.append(content).append(" - ");
            }
            int length = sb.length();
            sb.delete(length - 3, length);
            sb.append("\n");
        }
        log.debug(sb.toString());
    }

    private static class DigitUtil {
        private static <K extends Comparable<K>, V> int calculateContentDigit(BST.Node<K, V> root, Function<BST.Node<K, V>, String> toStringFunction) {
            List<BST.Node<K, V>> nodes = TraversalUtil.inOrder(root);
            int maxLength = 0;
            for (BST.Node<K, V> node : nodes) {
                int length = toStringFunction.apply(node).length();
                maxLength = Math.max(length, maxLength);
            }
            return ((maxLength % 2) == 0) ? maxLength : maxLength + 1;
        }

        private static int calculateElementDigit(int contentDigit) {
            return contentDigit + 2;
        }

        private static <K extends Comparable<K>, V> int calculateRowDigit(List<List<BST.Node<K, V>>> levelOrdered, int elementDigit) {
            int treeHeight = levelOrdered.size();
            int treeWidth = levelOrdered.get(treeHeight - 1).size();
            return treeWidth * elementDigit;
        }

        private static <K extends Comparable<K>, V> int calculateMarginDigit(List<BST.Node<K, V>> level, int elementDigit, int rowDigit) {
            int levelSize = level.size();
            int needfulDigit = levelSize * elementDigit;
            int needlessDigit = rowDigit - needfulDigit;
            return (needlessDigit / levelSize) / 2;
        }
    }


    private static class ConvertUtil {
        private static <K extends Comparable<K>, V> String nodeToSummary(BST.Node<K, V> node, Function<BST.Node<K, V>, String> toStringFunction) {
            return (node != null) ? toStringFunction.apply(node) : "";
        }

        private static String summaryToContent(String summary, int contentDigit) {
            final String CONTENT_FORMAT = "%" + contentDigit + "s";
            return String.format(CONTENT_FORMAT, summary);
        }

        private static String contentToElement(String content, int marginDigit) {
            final String MARGIN = " ".repeat(marginDigit);
            final String ELEMENT_FORMAT = MARGIN + "(%s)" + MARGIN;
            return String.format(ELEMENT_FORMAT, content);
        }
    }

}
