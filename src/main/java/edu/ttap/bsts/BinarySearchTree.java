package edu.ttap.bsts;

import java.util.ArrayList;
import java.util.List;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

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

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * @param node the root of the tree
     * @return the number of elements in the specified tree
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in this tree
     */
    public int size() {
        return sizeH(root);
    }

    /**
     * @param value the value to insert
     * @param cur   the current node in the recursion
     * @return the node at the current position in the tree
     */
    public Node<T> insertHelper(T value, Node<T> cur) {
        if (cur == null) {
            return new Node<>(value);
        } else {
            if (value.compareTo(cur.value) < 0) {
                cur.left = insertHelper(value, cur.left);
            } else {
                cur.right = insertHelper(value, cur.right);
            }
            return cur;
        }
    }

    /**
     * @param value the value to add to the tree
     */
    public void insert(T value) {
        root = insertHelper(value, root);
    }

    ///// Part 1: Contains

    /**
     * @param value the value to find
     * @param cur   the current node in the recursion
     * @return true iff the subtree rooted at cur contains <code>value</code>
     */
    private boolean containsHelper(T value, Node<T> cur) {
        boolean result = false;

        if (cur != null) {
            if (cur.value.compareTo(value) == 0) {
                result = true;
            } else if (cur.value.compareTo(value) < 0) {
                result = containsHelper(value, cur.right);
            } else {
                result = containsHelper(value, cur.left);
            }
        } else {
            return false;
        }

        return result;
    }

    /**
     * @param v the value to find
     * @return true iff this tree contains <code>v</code>
     */
    public boolean contains(T v) {
        return containsHelper(v, root);
    }

    ///// Part 2: Ordered Traversals

    /**
     * 
     * @param cur        the current node in the recursion
     * @param resultList the list to append values to in-order
     */
    private void toStringHelper(Node<T> cur, List<T> resultList) {
        if (cur == null) {
            return;
        } else {
            toStringHelper(cur.left, resultList);
            resultList.add(cur.value);
            toStringHelper(cur.right, resultList);
        }
    }

    /**
     * @return the (linearized) string representation of this BST
     */
    @Override
    public String toString() {
        String result = "[";

        List<T> resultList = new ArrayList<>();
        toStringHelper(root, resultList);

        for (int i = 0; i < resultList.size() - 1; i++) {
            result = result + resultList.get(i) + ", ";
        }

        return result += resultList.get(resultList.size() - 1) + "]";
    }

    /**
     * @param cur        the current node in the recursion
     * @param resultList the list to append values to in-order
     */
    public void toListHelper(Node<T> cur, List<T> resultList) {
        if (cur == null) {
            return;
        }

        toListHelper(cur.left, resultList);
        resultList.add(cur.value);
        toListHelper(cur.right, resultList);
    }

    /**
     * @return a list contains the elements of this BST in-order.
     */
    public List<T> toList() {
        List<T> result = new ArrayList<>();

        toListHelper(root, result);
        return result;
    }

    ///// Part 3: BST Sorting

    /**
     * @param <T> the carrier type of the lists
     * @param lst the list to sort
     * @return a copy of <code>lst</code> but sorted
     * @implSpec <code>sort</code> runs in O(n log(n)) time if the tree remains
     *           balanced.
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> lst) {
        BinarySearchTree<T> tree = new BinarySearchTree<T>();
        for (T t : lst) {
            tree.insert(t);
        }

        return tree.toList();
    }

    ///// Part 4: Deletion

    /*
     * The three cases of deletion are:
     * 1. Node value is less than delete value
     * 2. Node value is greater than delete value
     * 3. Node value is equal to delete value
     */

    /**
     * @param cur   the current node in the recursion
     * @param value the value to delete
     * @return the new subtree thats rooted at cur after deleting happens
     */
    public Node<T> deleteHelper(Node<T> cur, T value) {
        if (cur == null) {
            return null;
        }
        if (value.compareTo(cur.value) < 0) {
            cur.left = deleteHelper(cur.left, value);
        } else if (value.compareTo(cur.value) > 0) {
            cur.right = deleteHelper(cur.right, value);
        } else {
            if (cur.left == null) {
                return cur.right;
            } else if (cur.right == null) {
                return cur.left;
            } else {
                Node<T> previous = cur.left;
                while (previous.right != null) {
                    previous = previous.right;
                }
                cur.value = previous.value;
                cur.left = deleteHelper(cur.left, previous.value);
            }
        }
        return cur;
    }

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code>
     * found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        if (contains(value)) {
            deleteHelper(root, value);
        }
    }
}
