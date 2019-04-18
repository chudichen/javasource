package com.study.javasource.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 *
 * 学习编写HashMap实现，向前辈们致敬
 *
 * @author Michael.Chu
 * @date 2019-04-15 10:46
 */
public class HashMap<K, V> implements Map<K, V>, Cloneable, Serializable {

    private static final long serialVersionUID = 8497004991625828588L;

    /**
     * 默认初始容量值 - 必须为2的指数 (默认值 16)
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 最大容量，作为容器的最大值，可以通过构造方法传入。
     * 必须为2的指数。初始值为 1<<30。
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 扩容因子，如果构造器没有指定，则默认是0.75。
     * 当容器的容量达到，当前容量（默认16） * 负载因子（默认0.75）
     * 的时候，容器将进行扩容。
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 链表转化为红黑树的阈值，达到这个值的时候，会将链表转化为
     * 一颗红黑树，这个值应该必须大于2，最好是大于8的。
     */
    static final int TREEIFY_THRESHHOLD = 8;

    /**
     * 红黑树退化为链表的阈值，当达到这个值的时候，红黑树将会
     * 退化为链表。这个值要比树化的值小，最好小于6。
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * 容器树化之后的最小容量（初始值）。否则太多节点在一棵树上，
     * 就会进行resize操作。最好是 4 * 树化阈值。
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * 在Entry（键值对）中的基础hash节点。
     */
    static class Node<K, V> implements Map.Entry {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public final K getKey() {
            return key;
        }

        @Override
        public final V getValue() {
            return value;
        }

        @Override
        public final String toString() {
            return key + "=" + value;
        }

        @Override
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        @SuppressWarnings("unchecked")
        public V setValue(Object newValue) {
            V oldValue = value;
            value = (V) newValue;
            return oldValue;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue());
            }
            return false;
        }

        /* ---------------- 静态工具类 -------------- */
    }

    transient Node<K, V>[] table;

    transient Set<Map.Entry<K, V>> entrySet;

    transient int size;

    /**
     * 当前HashMap对象结构被修改的次数
     */
    transient int modCount;

    /**
     * 下一次要扩容{@code resize}操作的阈值(capacity * loadFactor)。
     *
     * @serial
     */
    int threshold;

    /**
     * 当前hash表的负载因子
     *
     * @serial
     */
    final float loadFactor;

    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity:" +
                    initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor:" +
                    loadFactor);
        }
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * 根据传入数字返回2的指数大小的容量值
     *
     * @param cap 传入参数
     * @return 返回容量值
     */
    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Override

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
