package com.study.javasource.util;

/**
 * @author Michael Chu
 * @date 2019-04-24 17:08
 */
public class LinkedHashMap extends HashMap implements Map {

    /**
     * 使用HashMap种的Node来构建LinkedHashMap的entry
     */
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K, V> before, after;
        Entry(int hash, K key, V value, HashMap.Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
}
