package com.study.javasource.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * 用于存储键值对的对象(key -> value)。Map集合中不能包含两个一样的key，
 * 而且每个key只能有一个value。
 *
 * @author Michael Chu
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

        /**
         * 只用指定的值来替换entry中的value值（可选项），（通过map来实现），
         * 如果当前entry已经被删除了就会返回undefined（通过iterator的
         * {@code remove}来实现）。
         *
         * @param value 要存入这个entry中的新值
         * @return 这个entry中的旧值
         * @throws UnsupportedOperationException 如果{@code put}不被
         *         当前Map所支持。
         * @throws ClassCastException
         * @throws NullPointerException
         * @throws IllegalArgumentException
         * @throws IllegalStateException
         */
        V setValue(V value);

        /**
         * 重写Object的{@code equals}方法
         *
         * @param o
         * @return
         */
        @Override
        boolean equals(Object o);

        /**
         * 重写Object的{@code hashCode}方法
         *
         * @return
         */
        @Override
        int hashCode();

        /**
         * 返回一个比较器，可以用于{@link Map.Entry}中的key按照自然排序进行比较
         *
         * @param <K> map中Key的类型,实现{@link Comparable}，可以进行比较
         * @param <V> map中value的类型
         * @return 一个用于比较{@link Map.Entry}中key的比较器
         */
        public static <K extends Comparable<? super K>, V> Comparator<Map.Entry<K, V>> comparingByKey() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> c1.getKey().compareTo(c2.getKey());
        }

        /**
         * 返回一个比较器，可以用于{@link Map.Entry}中的value按照自然排序进行比较
         *
         * @param <K> map中key的类型
         * @param <V> map中value的类型,实现{@link Comparable}，可以进行比较
         * @return 一个用于比较{@link Map.Entry}中value的比较器
         */
        public static <K, V extends Comparable<? super  V>> Comparator<Map.Entry<K, V>> comparingByValue() {
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        /**
         * 使用传入的比较器{@link Comparator}来比较{@link Map.Entry}中的key。
         *
         * @param <K> map中key的类型
         * @param <V> map中value的类型
         * @param cmp key的比较器{@link Comparator}
         * @return 用于比较 {@link Map.Entry}中的key的比较器
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        /**
         * 使用传入比较器{@link Comparator}来比较{@link Map.Entry}中的Value。
         *
         * @param <K> map中的key的类型
         * @param <V> map中的value的类型
         * @param cmp key的比较器{@link Comparator}
         * @return 用于比较 {@link Map.Entry}中的value的比较器
         */
        public static <K, V> Comparator<Map.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<Map.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }

    // 对照和hashing（散列）

    /**
     * 判断传入对象是否与当前map相等，如果相等，或者两者的映射关系表现
     * 一致则返回{@code true}。严格来讲，当{@code m1}与{@code m2}
     * 表现出一致的映射关系{@code m1.entrySet().equals(m2.entrySet())}
     * 的时候条件成立。这样就确保了{@code equals}这个方法在{@code Map}
     * 接口的不同实现类当中也可以正常的工作。
     *
     * @param o 用来和当前map做判断的对象
     * @return {@code true}表示相等
     */
    @Override
    boolean equals(Object o);

    /**
     * 返回这个map的hashCode值。这个hash值被定义为所有entry{@code entrySet()}
     * 的hashcode的总合。这样使得如果{@code m1.equals(m2)}成立，则
     * {@code m1.hashcode()==m2.hashcode()}也成立。
     *
     * @return 返回这个map的hashcode
     * @see Entry#hashCode()
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    @Override
    int hashCode();

    // 默认方法

    /**
     * 返回key所对应的值，如果没有匹配到则返回
     * {@code defaultValue}
     *
     * @implSpec
     * 默认的实现不保证现成安全，或者原子操作。
     * 现成安全的实现类要自己重写这个方法，并且使用
     * 自己维护的现成安全的属性。
     *
     * @param key 需要查找的key
     * @param defaultValue 如果没有查找到要返回的默认值
     * @return key所对应的value，如果没有找到则返回{@code defaultValue}
     * @throws ClassCastException 如果key不能转换成map种的key类型
     * @throws NullPointerException 如果key为null，而这个map不允许有null的key
     */
    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key))
                ? v
                : defaultValue;
    }

    /**
     * 返回entry流，直到所有的entry都被处理了，或者抛出了异常。
     * 并且会按照iterator相同的顺序进行遍历（如果指定了迭代顺序的话）。
     * 异常会抛给调用者。
     *
     * @implSpec
     * 默认的实现方法等价于，{@code map}:
     * <pre>
     *     {code
     *         for (Map.Entry<K, V> entry : map.entrySet())
     *             action.accept(entry.getKey(), entry.getValue())
     *     }
     * </pre>
     *
     * 默认的实现不保证现成安全，或者原子操作。
     * 现成安全的实现类要自己重写这个方法，并且使用
     * 自己维护的现成安全的属性。
     *
     * @param action 表现为要执行遍历的entry
     * @throws NullPointerException 如果指定的action为null
     * @throws ConcurrentModificationException 在遍历时map结构发生变化
     * @since 1.8
     */
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                // 通常来讲这时表示，当前entry已经不属于这个map
                throw new ConcurrentModificationException();
            }
            action.accept(k, v);
        }
    }

    /**
     *
     * @param function
     */
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);

    }
}
