package com.company;

import java.util.function.Consumer;

public class LinkedList<E> {

    protected static class Node<T> {
        Node<T> next, previous;
        T value;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    protected int size = 0;
    protected Node<E> first, last;

    public LinkedList() {
    }

    protected void checkElementIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    protected Node<E> getNode(int index) {
        Node<E> node;
        if (index < (size / 2)) {
            node = first;
            for (int i = 0; i < index; i++)
                node = node.next;
        } else {
            node = last;
            for (int i = size - 1; i > index; i--)
                node = node.previous;
        }
        return node;
    }

    protected void linkLast(E element) {
        final Node<E> last = this.last;
        final Node<E> newNode = new Node<>(last, element, null);
        if (last == null)
            first = newNode;
        else
            last.next = newNode;
        this.last = newNode;
        size++;
    }
    protected void linkBefore(E element, Node<E> node) {
        final Node<E> previous = node.previous;
        final Node<E> newNode = new Node<>(previous, element, node);
        node.previous = newNode;
        if (previous == null)
            first = newNode;
        else
            previous.next = newNode;
        size++;
    }

    protected E unlink(Node<E> node) {
        final E element = node.value;
        final Node<E> next = node.next;
        final Node<E> prev = node.previous;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.previous = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.previous = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return element;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E element) {
        linkLast(element);
    }

    public E get(int index) {
        checkElementIndex(index);
        return getNode(index).value;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.value == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<E> node = first; node != null; node = node.next) {
                if (o.equals(node.value)) {
                    unlink(node);
                    return true;
                }
            }
        }
        return false;
    }
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }
    public void clear() {
        Node<E> next;
        for (Node<E> node = first; node != null; node = next) {
            next = node.next;
            node.value = null;
            node.next = null;
            node.previous = null;
        }
        first = last = null;
        size = 0;
    }

    public int indexOf(Object object) {
        int index = 0;
        if (object == null) {
            for (Node<E> node = first; node != null; node = node.next) {
                if (node.value == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (object.equals(x.value))
                    return index;
                index++;
            }
        }
        return -1;
    }
    public boolean contains(Object object) {
        return indexOf(object) >= 0;
    }

    public void forEach(Consumer<? super E> consumer) {
        for (Node<E> node = first; node != null; node = node.next)
            consumer.accept(node.value);
    }
    public void descending(Consumer<? super E> consumer) {
        for (Node<E> node = last; node != null; node = node.previous)
            consumer.accept(node.value);
    }
}