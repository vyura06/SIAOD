package sample;

import java.util.Comparator;
import java.util.function.Consumer;

public class Tree<E> {
    static class Node<E> {
        E key;
        Node<E> left, right;

        private Node(E key) {
            this.key = key;
        }
    }

    private static final Comparator<?> toStringComparator = (o1, o2) -> {
        if (o1 == null) {
            return o2 == null ? 0 : -1;
        } else {
            return o2 == null ? 1 : o1.toString().compareTo(o2.toString());
        }
    };

    private final Comparator<? super E> comparator;
    Node<E> root = null;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public Tree() {
        this.comparator = (Comparator<? super E>) toStringComparator;
    }
    public Tree(Comparator<? super E> comparator) {
        if (comparator == null)
            throw new NullPointerException();
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public void put(E key) {
        root = insert(root, key);
    }
    private Node<E> insert(Node<E> node, E key) {
        if (node == null) {
            size++;
            return new Node<>(key);
        }
        int cmp = comparator.compare(key, node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key);
        } else if (cmp > 0) {
            node.right = insert(node.right, key);
        }
        return node;
    }

    private Node<E> getNode(E key) {
        int cmp;
        Node<E> node = root;
        while (node != null) {
            cmp = comparator.compare(key, node.key);
            if (cmp < 0)
                node = node.left;
            else if (cmp > 0)
                node = node.right;
            else
                return node;
        }
        return null;
    }
    public boolean contains(E key) {
        return getNode(key) != null;
    }

    public boolean remove(E key) {
        Node<E> parent = null;
        Node<E> node = root;
        int cmp;
        while (node != null) {
            cmp = comparator.compare(key, node.key);
            if (cmp < 0) {
                parent = node;
                node = node.left;
            } else if (cmp > 0) {
                parent = node;
                node = node.right;
            } else {
                delete(parent, node);
                return true;
            }
        }
        return false;
    }
    private void delete(Node<E> parent, Node<E> node) {
        if (parent == null) { //entry == root
            if (node.left == null) {
                root = node.right;
                invalidate(node);
            } else {
                del(node);
            }
        } else {
            if (node.left == null) {
                if (node == parent.left) {
                    parent.left = node.right;
                } else if (node == parent.right) {
                    parent.right = node.right;
                }
                invalidate(node);
            } else {
                del(node);
            }
        }
        size--;
    }
    private void del(Node<E> node) {
        Node<E> parent = node;
        Node<E> temp = node.left;
        while (temp.right != null) {
            parent = temp;
            temp = temp.right;
        }
        node.key = temp.key;
        if (parent == node) {
            parent.left = temp.left;
        } else {
            parent.right = temp.left;
        }
        invalidate(temp);
    }
    private void invalidate(Node<E> node) {
        node.key = null;
        node.left = null;
        node.right = null;
    }

    public void clear() {
        while (root != null)
            delete(null, root);
    }

    public void preorder(Consumer<E> consumer) {
        preorder(root, consumer);
    }
    private void preorder(Node<E> node, Consumer<E> consumer) {
        if (node != null) {
            consumer.accept(node.key);
            inorder(node.left, consumer);
            inorder(node.right, consumer);
        }
    }

    public void inorder(Consumer<E> consumer) {
        inorder(root, consumer);
    }
    private void inorder(Node<E> node, Consumer<E> consumer) {
        if (node != null) {
            inorder(node.left, consumer);
            consumer.accept(node.key);
            inorder(node.right, consumer);
        }
    }

    public void postorder(Consumer<E> consumer) {
        postorder(root, consumer);
    }
    private void postorder(Node<E> node, Consumer<E> consumer) {
        if (node != null) {
            inorder(node.left, consumer);
            inorder(node.right, consumer);
            consumer.accept(node.key);
        }
    }
}