package com.study.javasource.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author Michael.Chu
 * @date 2019-04-15 10:46
 */
public class HashMap<K,V> implements Map<K,V>, Cloneable, Serializable {

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

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object key) {
        return false;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V get(Object key) {
        return null;
    }

    public V put(K key, V value) {
        return null;
    }

    public V remove(K key) {
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> m) {

    }

    public void clear() {

    }

    public Set<K> keySet() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
