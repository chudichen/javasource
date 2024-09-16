package com.study.javasource.util;

import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

/**
 *
 * 学习编写HashMap实现，向前辈们致敬
 *
 * @author Michael Chu
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


    }

    /* ---------------- 静态工具类 -------------- */

    /**
     * 计算{@code key.hashCode()}并且将得到的hash值的高位移到
     * 低位(XORs)
     * @param key 需要计算hash值的key
     * @return 重新计算的hash值
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
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

    /* -------------------- 字段 ------------------ */

    /**
     * 这个map的表，在第一次使用时初始化，以及在必要的时候
     * 进行扩容，当已经初始化了，数组的长度始终是2的指数。
     * （我们也接受数组长度为0，为了某些现在没有用到的自动化机制）
     */
    transient Node<K, V>[] table;

    /**
     * 保存着entrySet()的值。注意的是AbstractMap使用的是
     * keySet()和valueSet()
     */
    transient Set<Map.Entry<K, V>> entrySet;

    /**
     * map当中键值对（entry）的数量。
     */
    transient int size;

    /**
     * 当前HashMap被结构更改的次数。比如，更新键值对的数量，
     * 以及内部的一些更改（rehash）。这个字段被用来在迭代时
     * 来进行fail-fast（ConcurrentModificationException）。
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

    /* ---------------- Public 级别的操作 -------------- */

    /**
     * 通过指定的加载因子和初始化容量构建一个空的{@code HashMap}。
     *
     * @param initialCapacity 初始化容量
     * @param loadFactor 加载因子
     * @throws IllegalStateException 初始化容量是负数，以及加载因子不合法
     */
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
     * 通过指定一个初始化容量，并且使用默认的加载因子（0.75）来构造一个{@code HashMap}
     *
     * @param initialCapacity 初始化容量
     * @throws IllegalStateException 初始化容量是负数，以及加载因子不合法
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 使用默认的初始化容量（16）以及加载因子（0.75）来构建一个空的{@code HashMap}。
     */
    public HashMap() {
        // 所有的字段已经是默认值了。
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    /**
     * 根据一个传入的Map来构建一个新的{@code HashMap}，拷贝传入的
     * 键值对映射。使用默认的加载因子（0.75），初始化容量取决了传入的
     * map。
     *
     * @param m 需要进行全拷贝的map
     * @throws NullPointerException 如果传入的map为null
     */
    public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }

    /**
     * 实现了Map.putAll以及Map的构造器
     *
     * @param m 传入map
     * @param evict false表示在构造时创建，true表示在使用时候创建
     */
    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {
            // 之前的值
            if (table == null) {
                float ft = ((float) s / loadFactor) + 1.0F;
                int t = ((ft < (float) MAXIMUM_CAPACITY) ?
                        (int) ft : MAXIMUM_CAPACITY);
                if (t > threshold) {
                    threshold = tableSizeFor(t);
                }
            } else if (s > threshold) {
                resize();
            }
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false , evict);
            }
        }
    }

    /**
     * 初始化或者将table翻倍。如果是null，根据初始化容量以及阈值
     * 来进行table的分配。否则，进行扩展，因为我们是以二次幂进行扩展
     * 的，因此原来的元素要么保持不变，要么在新的table向后移动两个偏移量。
     *
     * @return table（HashMap的存储表）
     */
    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        return null;
    }

    /**
     * 实现自Map.put以及相关的方法。
     *
     * @param hash key的hash值
     * @param key key
     * @param value 等待put的value
     * @param onlyIfAbsent 如果为true，不改变现有值
     * @param evict 如果是false，当前table进入创建模式。
     * @return 返回之前的值，如果没有返回null
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        }
        if ((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = newNode(hash, key, value, null);
        } else {
            Node<K,V> e; K k;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            } else if (p instanceof TreeNode) {
//                e = ((TreeNode<K,V>)p).
            }
        }
        return null;
    }

    /**
     * package界别的保护方法，这个方法被LinkedList重写。
     * 创建一个常规节点（非树）。
     *
     * @param hash hash值
     * @param key key
     * @param value value
     * @param next 下一个节点
     * @return 节点
     */
    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
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
