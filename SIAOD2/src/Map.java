import java.util.function.BiConsumer;

public class Map<K, V> {
    public static class Entry<K, V> {
        private final int hash;
        private final K key;
        private V value;
        private Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }

    private Entry<K, V>[] buckets;
    private int size = 0;

    public Map() {
        this(16);
    }
    @SuppressWarnings("unchecked")
    public Map(int countBuckets) {
        if (countBuckets < 0)
            throw new IllegalArgumentException("Illegal capacity: " + countBuckets);
        this.buckets = ((Entry<K, V>[]) new Entry[countBuckets]);
    }

    private int indexOf(int hash, int nemBuckets) {
        return hash % nemBuckets;
    }
    private static int hash(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    private Entry<K, V> getNode(Object key) {
        int index = indexOf(hash(key), buckets.length);
        Entry<K, V> entry = buckets[index];
        if (key == null) {
            while (entry != null) {
                if (null == entry.key)
                    return entry;
                entry = entry.next;
            }
        } else {
            while (entry != null) {
                if (key.equals(entry.key))
                    return entry;
                entry = entry.next;
            }
        }
        return null;
    }

    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    public V get(Object key) {
        Entry<K, V> entry = getNode(key);
        return entry == null ? null : entry.value;
    }

    public V put(K key, V value) {
        ensureCapacity();
        final int hash = hash(key);
        final int index = indexOf(hash, buckets.length);
        Entry<K, V> entry = buckets[index];
        if (key == null) {
            while (entry != null) {
                if (null == entry.key)
                    return entry.setValue(value);
                entry = entry.next;
            }
        } else {
            while (entry != null) {
                if (key.equals(entry.key))
                    return entry.setValue(value);
                entry = entry.next;
            }
        }
        Entry<K, V> next = buckets[index];
        Entry<K, V> newEntry = new Entry<>(hash, key, value, next);
        buckets[index] = newEntry;
        size++;
        return null;
    }
    private void ensureCapacity() {
        if (size >= buckets.length) {
            int newSize = (buckets.length + 1) << 1; //grow x2

            @SuppressWarnings("unchecked")
            final Entry<K, V>[] entries = ((Entry<K, V>[]) new Entry[newSize]);
            //copy elements
            for (Entry<K, V> entry : buckets)
                if (entry != null)
                    entries[indexOf(entry.hash, newSize)] = entry;
            buckets = entries;
        }
    }

    public V remove(Object key) {
        int index = indexOf(hash(key), buckets.length);
        Entry<K, V> prev = null;
        Entry<K, V> entry = buckets[index];
        if (key == null) {
            while (entry != null) {
                if (null == entry.key)
                    return removeNode(index, prev, entry);
                prev = entry;
                entry = entry.next;
            }
        } else {
            while (entry != null) {
                if (key.equals(entry.key))
                    return removeNode(index, prev, entry);
                prev = entry;
                entry = entry.next;
            }
        }
        return null;
    }
    private V removeNode(int index, Entry<K, V> prev, Entry<K, V> removed) {
        if (prev == null) {
            buckets[index] = null;
        } else {
            prev.next = removed.next;
        }
        removed.next = null;
        return removed.setValue(null);
    }

    public void clear() {
        Entry<K, V> entry, next;
        for (int i = 0; i < buckets.length; i++) {
            entry = buckets[i];
            buckets[i] = null;
            while (entry != null) {
                next = entry.next;

                entry.next = null;
                entry.value = null;
                entry = next;
            }
        }
        size = 0;
    }

    public void forEach(BiConsumer<? super K, ? super V> consumer) {
        for (Entry<K, V> entry : buckets) {
            while (entry != null) {
                consumer.accept(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }
}