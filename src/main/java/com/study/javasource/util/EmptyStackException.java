package com.study.javasource.util;

/**
 * 被{@code Stack}中的方法抛出，用来表示
 * 当前栈中的元素为空。
 *
 * @author Michael Chu
 * @date 2019-04-19 10:37
 */
public class EmptyStackException extends RuntimeException {

    private static final long serialVersionUID = -9085537677872335676L;

    /**
     * 创建一个{@code EmptyStackException}
     */
    public EmptyStackException() {}
}
