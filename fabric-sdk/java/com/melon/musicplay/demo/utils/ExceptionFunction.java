package com.melon.musicplay.demo.utils;

@FunctionalInterface
public interface ExceptionFunction<T, R>{
    public R apply(T t) throws Exception;
}
