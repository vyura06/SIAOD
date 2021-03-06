package com.company;

import java.util.Comparator;
import java.util.Objects;

public class SortedLinkedList<E> extends LinkedList<E> {
    public final Comparator<E> comparator;

    public SortedLinkedList(Comparator<E> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
    }

    public void add(E element) {
        for (Node<E> node = first; node != null; node = node.next) {
            if (comparator.compare(element, node.value) < 0) {
                linkBefore(element, node);
                return;
            }
        }
        linkLast(element);
    }
}