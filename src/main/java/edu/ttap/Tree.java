package edu.ttap;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic binary tree implementation.
 */
public class Tree<T> {
    /**
     * A node of the binary tree.
     */
    public static class Node<T> {
        public T value;
        public Node<T> left;
        public Node<T> right;

        /**
         * @param value the value of the node
         * @param left  the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }

    ///// From the reading...

    private Node<T> root;

    /**
     * Constructs a new, empty binary tree.
     */
    public Tree() {
        this.root = null;
    }

    /**
     * @return a sample binary tree for testing purposes
     */
    public static Tree<Integer> makeSampleTree() {
        Tree<Integer> tree = new Tree<Integer>();
        tree.root = new Node<>(
                5,
                new Node<>(2,
                        new Node<>(1),
                        new Node<>(3)),
                new Node<>(8,
                        new Node<>(7,
                                new Node<>(6),
                                null),
                        new Node<>(9,
                                null,
                                new Node<>(10))));
        return tree;
    }

    /**
     * @param node the root of the tree
     * @return the number elements found in this tree rooted at node
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /** @return the number of elements in the tree */
    public int size() {
        return sizeH(root);
    }

    ///// Part 1: Contains

    /**
     * @param value The value we are looking for.
     * @param cur The current node we are checking.
     * @return true if the value is found in this node or any of its children, false
     *         otherwise.
     */
    private boolean containsHelper(T value, Node<T> cur) {
        if (cur == null) {
            return false;
        } else if (cur.value == value) {
            return true;
        } else {
            return containsHelper(value, cur.left) || containsHelper(value, cur.right);
        }
    }

    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        Node<T> cur = root;

        return containsHelper(value, cur);
    }

    ///// Part 2: Traversals

    /**
     * @param cur The current node being visited.
     * @param resultList The list where all found values are collected.
     */
    private void inorderHelper(Node<T> cur, List<T> resultList) {
        if (cur == null) {
            return;
        }
        inorderHelper(cur.left, resultList);
        resultList.add(cur.value);
        inorderHelper(cur.right, resultList);
    }

    /**
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> result = new ArrayList<>();

        inorderHelper(this.root, result);
        return result;
    }

    /**
     * @param cur The current node being visited.
     * @param resultList The single list where all found values are collected.
     */
    private void preorderHelper(Node<T> cur, List<T> resultList) {
        if (cur == null) {
            return;
        }
        resultList.add(cur.value);
        preorderHelper(cur.left, resultList);
        preorderHelper(cur.right, resultList);
    }

    /**
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        List<T> result = new ArrayList<>();

        preorderHelper(this.root, result);
        return result;
    }

    /**
     * @param cur The current node being visited.
     * @param resultList The single list where all found values are collected.
     */
    private void postorderHelper(Node<T> cur, List<T> resultList) {
        if (cur == null) {
            return;
        }
        postorderHelper(cur.left, resultList);
        postorderHelper(cur.right, resultList);
        resultList.add(cur.value);
    }

    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> result = new ArrayList<>();

        postorderHelper(this.root, result);
        return result;
    }

    ///// Part 3: Stringifying Trees

    /**
     * @return a string represent of this tree in the form, "[x1, ..., xk]."
     *         The order of the elements is left unspecified.
     */
    @Override
    public String toString() {
        List<T> list = this.toListInorder();
        return list.toString();
    }

    ///// Extra: Pretty Printing

    /**
     * @return a string represent of this tree in bulleted list form.
     */
    public String toPrettyString() {
        throw new UnsupportedOperationException();
    }

    /**
     * The main driver for this program
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Nothing to do. 'Run' via the JUnit tests instead!");
    }
}