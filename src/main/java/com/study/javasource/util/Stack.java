package com.study.javasource.util;


/**
 * @author Michael Chu
 * @date 2019-04-19 10:29
 */
public class Stack<E> {
    /**
     * 创建空的Stack
     */
    public Stack() {}

    /**
     * 将元素放入栈顶，这和<blockquote><pre>
     * addElement</pre></blockquote>效果类似
     *
     * @param item 需要放入栈顶的元素
     * @return 这个添加的元素
     */
    public E push(E item) {
        return item;
    }

    /**
     * 弹出栈顶元素（返回并删除）
     *
     * @return 栈顶的元素
     * @throws EmptyStackException
     */
    @SuppressWarnings("JavaDoc")
    public synchronized E pop() {
        E obj;
        int len = size();
        obj = peek();
        removeElementAt(len - 1);
        return obj;
    }

    private void removeElementAt(int i) {

    }

    /**
     * 返回栈顶元素，并不删除
     *
     * @return 位于栈顶的元素（{@code Vector}中的最后的一个元素）
     * @throws EmptyStackException 如果这个Stack是空的
     */
    private synchronized E peek() {
        int len = size();
        if (len == 0) {
            throw new EmptyStackException();
        }
        return elementAt(len - 1);
    }

    /**
     * 检测当前栈是否为空
     *
     * @return 如果不为空返回{@code true},否则
     *         回{@code false}
     */
    private boolean empty() {
        return size() == 0;
    }

    private E elementAt(int i) {
        return null;
    }

    private int size() {
        return 0;
    }
}
