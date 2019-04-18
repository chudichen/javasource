package com.study.javasource.util;

import java.util.Collection;
import java.util.Set;

/**
 * 用于存储键值对的对象(key -> value)。Map集合中不能包含两个一样的key，
 * 而且每个key只能有一个value。
 *
 * @author Michael.Chu
 * @date 2019-04-15 10:45
 */
public interface Map<K,V> {
    // 查询方法

    /**
     * 返回key-value对的数量
     *
     * @return 返回key-value对的数量
     */
    int size();

    /**
     * 返回是否为空
     *
     * @return 返回是否为空
     */
    boolean isEmpty();

    /**
     * 是否含有目标key
     *
     * @param key 需要检查的key
     * @return 是否含有目标key
     */
    boolean containsKey(Object key);

    /**
     * 是否包含有目标value
     *
     * @param value 需要检查的value
     * @return 是否包含有目标value
     */
    boolean containsValue(Object value);

    /**
     * 通过key来获取value对象
     *
     * @param key 传入的key
     * @return 找到了则返回，对于的value，没找到则返回空
     */
    V get(Object key);

    // 修改方法

    /**
     * 将特定的键值对插入集合中，没有则新建，有则覆盖。
     *
     * @param key 键
     * @param value 值
     * @return 返回前一个值，新的元素则返回<tt>null</tt>。ps:之前还真没发现put方法有返回值。
     */
    V put(K key,V value);

    /**
     * 根据指定key删除元素。
     *
     * @param key key
     * @return 删除key的value，如果不存在则返回<tt>null</tt>
     */
    V remove(K key);

    // 批量操作

    /**
     * 将所有m中的元素放入当前集合中
     *
     * @param m 参数
     */
    void putAll(Map<? extends K, ? extends V> m);

    /**
     * 清空集合当中的所有元素
     */
    void clear();

    /**
     * 返回当前集合当中的所有key
     *
     * @return 返回当前集合当中的所有key
     */
    Set<K> keySet();

    /**
     * 返回集合当中的所有value
     */
    Collection<V> values();

    /**
     * 返回集合当中所有的键值对
     *
     * @return
     */
    Set<Map.Entry<K, V>> entrySet();

    interface Entry<K, V> {
        /**
         * 返回key
         *
         * @return key
         */
        K getKey();

        /**
         * 返回在entry中所对应的value，如果这个entry已经从集合中删除了
         * (通过迭代器iterator的{@code remove}方法删除),返回结果将会是
         * undefined.
         *
         * @return 返回这个entry中的value
         * @throws IllegalStateException 建议实现这个异常，但是不是强制要求。
         *                               在调用这个方法时，目标entry被删除，
         *                               可以抛出这个异常。
         */
        V getValue();
    }
}
