import java.util.NoSuchElementException;

public class Queue<E> {
    private static class Node<E> {
        Node<E> next;
        E value;

        Node(E value) {
            this.value = value;
        }
    }

    private int size = 0;
    private Node<E> first, last;

    public Queue() {
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean offer(E e) {
        Node<E> newNode = new Node<>(e);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
        return true;
    }
    public boolean contains(Object o) {
        Node<E> node = first;
        if (o == null) {
            while (node != null) {
                if (null == node.value)
                    return true;
                node = node.next;
            }
        } else {
            while (node != null) {
                if (o.equals(node.value))
                    return true;
                node = node.next;
            }
        }
        return false;
    }

    private void removeNode(Node<E> previous, Node<E> node) {
        Node<E> next = node.next;

        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
        }

        if (next == null) {
            last = previous;
        } else {
            node.next = null;
        }

        node.value = null;
        size--;
    }
    public boolean remove(Object o) {
        Node<E> previous = null;
        Node<E> node = first;
        if (o == null) {
            while (node != null) {
                if (null == node.value) {
                    removeNode(previous, node);
                    return true;
                }
                previous = node;
                node = node.next;
            }
        } else {
            while (node != null) {
                if (o.equals(node.value)) {
                    removeNode(previous, node);
                    return true;
                }
                previous = node;
                node = node.next;
            }
        }
        return false;
    }

    public void clear() {
        Node<E> next;
        for (Node<E> node = first; node != null; node = next) {
            next = node.next;
            node.next = null;
            node.value = null;
        }
        size = 0;
    }

    private E unlinkFirst(Node<E> f) {
        E value = f.value;
        first = f.next;
        if (first == null)
            last = null;
        f.next = null;
        f.value = null;
        size--;
        return value;
    }
    public E remove() {
        final Node<E> first = this.first;
        if (first == null)
            throw new NoSuchElementException();
        return unlinkFirst(first);
    }
    public E poll() {
        final Node<E> f = first;
        return (f == null) ? null : unlinkFirst(f);
    }

    public E element() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.value;
    }
    public E peek() {
        final Node<E> f = first;
        return f == null ? null : f.value;
    }

    @Override
    public String toString() {
        if (first == null)
            return "[]";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        Node<E> next;
        Node<E> node = first;
        while (true) {
            next = node.next;
            stringBuilder.append(node.value);
            if (next == null)
                return stringBuilder.append(']').toString();
            stringBuilder.append(',').append(' ');
            node = next;
        }
    }
}