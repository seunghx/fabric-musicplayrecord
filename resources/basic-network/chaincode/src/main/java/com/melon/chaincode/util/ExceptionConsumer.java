package com.melon.chaincode.util;

import java.util.function.Consumer;

@FunctionalInterface
public interface ExceptionConsumer<T> {
    void accept(T t) throws Exception;
}
