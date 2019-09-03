package com.melon.musicplay.demo.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

import org.hyperledger.fabric.sdk.security.CryptoSuite;

@Slf4j
public class Util {

    public static <T,R> Function<T, R> wrappedFunction(ExceptionFunction<T, R> mapperFunction) {
        return argument -> {
            try{
                return mapperFunction.apply(argument);
            }catch(Exception e){
                throw new RuntimeException("Exception occurred at wrapped function ", e);
            }
        };
    }

    public static CryptoSuite defaultCryptoSuite(){
        try{
            log.debug("Returnning crypto suite");
            return CryptoSuite.Factory.getCryptoSuite();
        }catch(Exception e){
            throw new RuntimeException("Exception occurred while trying to get crypto suite", e);
        }
    }
}
