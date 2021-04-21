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

    protected Node<E> first, last;

    public LinkedList() {
    }

    protected void linkLast(E element) {
        final Node<E> last = this.last;
        final Node<E> newNode = new Node<>(last, element, null);
        if (last == null)
            first = newNode;
        else
            last.next = newNode;
        this.last = newNode;
    }
    protected void linkBefore(E element, Node<E> node) {
        final Node<E> previous = node.previous;
        final Node<E> newNode = new Node<>(previous, element, node);
        node.previous = newNode;
        if (previous == null)
            first = newNode;
        else
            previous.next = newNode;
    }

    public void add(E element) {
        linkLast(element);
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