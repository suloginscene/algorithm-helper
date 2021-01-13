package com.github.suloginscene.algorithmhelper.core.binarysearchtree;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
class PrintUtil {

    /**
     * [ row > element > content > summary ]
     * Row have many elements.
     * Element wrap content in parenthesis and has margin.
     * Content normalize length of summary.
     * Summary is string from node.toSummary().
     */
    protected static <K extends Comparable<K>, V> void print(@NonNull BinarySearchTree.Node<K, V> root) {
        int height = MetricUtil.height(root);
        if (height > 7) {
            log.warn("\n> Too big to print (Height: " + height + ", Root: " + root.toSummary() + ")\n");
            return;
        }

        StringBuilder sb = new StringBuilder();
        List<List<BinarySearchTree.Node<K, V>>> levelOrdered = TraversalUtil.levelOrder(root);

        int contentDigit = DigitUtil.calculateContentDigit(root);
        int elementDigit = DigitUtil.calculateElementDigit(contentDigit);
        int rowDigit = DigitUtil.calculateRowDigit(levelOrdered, elementDigit);

        for (List<BinarySearchTree.Node<K, V>> level : levelOrdered) {
            int marginDigit = DigitUtil.calculateMarginDigit(level, elementDigit, rowDigit);

            for (BinarySearchTree.Node<K, V> node : level) {
                String summary = ConvertUtil.nodeToSummary(node);
                String content = ConvertUtil.summaryToContent(summary, contentDigit);
                String element = ConvertUtil.contentToElement(content, marginDigit);
                sb.append(element);
            }
            sb.append("\n");
        }

        log.info("\n> Print Tree\n" + sb.toString());
    }


    private static class DigitUtil {
        private static <K extends Comparable<K>, V> int calculateContentDigit(BinarySearchTree.Node<K, V> root) {
            List<BinarySearchTree.Node<K, V>> nodes = TraversalUtil.inOrder(root);
            int maxLength = 0;
            for (BinarySearchTree.Node<K, V> node : nodes) {
                int length = node.toSummary().length();
                maxLength = Math.max(length, maxLength);
            }
            return ((maxLength % 2) == 0) ? maxLength : maxLength + 1;
        }

        private static int calculateElementDigit(int contentDigit) {
            return contentDigit + 2;
        }

        private static <K extends Comparable<K>, V> int calculateRowDigit(List<List<BinarySearchTree.Node<K, V>>> levelOrdered, int elementDigit) {
            int treeHeight = levelOrdered.size();
            int treeWidth = levelOrdered.get(treeHeight - 1).size();
            return treeWidth * elementDigit;
        }

        private static <K extends Comparable<K>, V> int calculateMarginDigit(List<BinarySearchTree.Node<K, V>> level, int elementDigit, int rowDigit) {
            int levelSize = level.size();
            int needfulDigit = levelSize * elementDigit;
            int needlessDigit = rowDigit - needfulDigit;
            return (needlessDigit / levelSize) / 2;
        }
    }


    private static class ConvertUtil {
        private static <K extends Comparable<K>, V> String nodeToSummary(BinarySearchTree.Node<K, V> node) {
            return (node != null) ? node.toSummary() : "";
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
