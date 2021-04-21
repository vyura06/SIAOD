package sample;

import java.util.function.IntConsumer;

public class Tree {
    static class Node {
        int key;
        Node left, right;
        boolean leftThread, rightThread;
    }

    final Node root;
    private int size = 0;

    public Tree() {
        Node node = new Node();
        node.left = node.right = node;
        node.leftThread = node.rightThread = true;
        node.key = Integer.MAX_VALUE;
        root = node;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    Node search(int key) {
        Node node = root.left;
        while (true) {
            if (node.key < key) {
                if (node.rightThread)
                    return null;
                node = node.right;
            } else if (node.key > key) {
                if (node.leftThread)
                    return null;
                node = node.left;
            } else {
                return node;
            }
        }
    }
    public boolean contains(int o) {
        return search(o) != null;
    }

    public boolean add(int key) {
        Node p = root;
        while (true) {
            if (p.key < key) {
                if (p.rightThread)
                    break;
                p = p.right;
            } else if (p.key > key) {
                if (p.leftThread)
                    break;
                p = p.left;
            } else {
                return false; // redundant key
            }
        }

        Node tmp = new Node();
        tmp.key = key;
        tmp.rightThread = tmp.leftThread = true;
        if (p.key < key) { // insert to right side
            tmp.right = p.right;
            tmp.left = p;
            p.right = tmp;
            p.rightThread = false;
        } else {
            tmp.right = p;
            tmp.left = p.left;
            p.left = tmp;
            p.leftThread = false;
        }
        size++;
        return true;
    }
    public boolean remove(int key) {
        Node dest = root.left, p = root;
        while (true) {
            if (dest.key < key) {
                if (dest.rightThread)
                    return false; // not found
                p = dest;
                dest = dest.right;
            } else if (dest.key > key) {
                if (dest.leftThread)
                    return false; // not found
                p = dest;
                dest = dest.left;
            } else {
                break; // bingo
            }
        }
        Node target = dest;
        if (!dest.rightThread && !dest.leftThread) { // dest has two children
            p = dest;
            // find largest node at left child
            target = dest.left;
            while (!target.rightThread) {
                p = target;
                target = target.right;
            }
            dest.key = target.key; // using replace node
        }
        if (p.key >= target.key) {
            if (target.rightThread && target.leftThread) {
                //   p
                //  /
                //  t
                p.left = target.left;
                p.leftThread = true;
            } else if (target.rightThread) {
                //      p
                //     /
                //    t
                //   /
                // t.left
                Node largest = target.left;
                while (!largest.rightThread) {
                    largest = largest.right;
                }
                largest.right = p;
                p.left = target.left;
            } else {
                //      p
                //     /
                //    t
                //     \
                //     t.right
                Node smallest = target.right;
                while (!smallest.leftThread) {
                    smallest = smallest.left;
                }
                smallest.left = target.left;
                p.left = target.right;
            }
        } else {
            if (target.rightThread && target.leftThread) {
                //   p
                //    \
                //     t
                p.right = target.right;
                p.rightThread = true;
            } else if (target.rightThread) {
                //   p
                //    \
                //    t
                //   /
                // t.left
                Node largest = target.left;
                while (!largest.rightThread) {
                    largest = largest.right;
                }
                largest.right = target.right;
                p.right = target.left;
            } else {
                //   p
                //    \
                //    t
                //     \
                //   t.right
                Node smallest = target.right;
                while (!smallest.leftThread) {
                    smallest = smallest.left;
                }
                smallest.left = p;
                p.right = target.right;
            }
        }
        size--;
        return true;
    }

    public void clear() {
        while (size != 0)
            remove(root.left.key);
    }

    public void ascending(IntConsumer consumer) {
        Node node = root, parent;
        while (true) {
            parent = node;
            node = node.right;
            if (parent == root || !parent.rightThread) {
                while (!node.leftThread) {
                    node = node.left;
                }
            }
            if (node == root)
                break;
            consumer.accept(node.key);
        }
    }
    public void descending(IntConsumer consumer) {
        Node node = root, parent;
        while (true) {
            parent = node;
            node = node.left;
            if (!parent.leftThread) {
                while (!node.rightThread) {
                    node = node.right;
                }
            }
            if (node == root)
                break;
            consumer.accept(node.key);
        }
    }
}