package com.study.javasource.util;

/**
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

}
