package com.melon.chaincode.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class Util {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");

    static{
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static byte[] jsonRepresentationAsByte(Object object){
        try{
            return objectMapper.writeValueAsBytes(object);
        }catch(IOException e){
            log.error("Exception occured while trying to write object as JSON bytes", e);
            throw new RuntimeException(e);
        }
    }

    public static String jsonRepresentationAsString(Object object){
        try{
            return objectMapper.writeValueAsString(object);
        }catch(IOException e){
            log.error("Exception occurred while trying to write object as JSON string", e);
            throw new RuntimeException(e);
        }
    }

    public static JsonNode readObjectTreeFromJsonString(String jsonString){
        try{
            return objectMapper.readTree(jsonString);
        }catch(IOException e){
            log.error("Exception occurred while trying to read tree from JSON string");
            throw new RuntimeException(e);
        }
    }

    public static <T> T readObjectFromJsonString(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Consumer<T> wrapConsumer(ExceptionConsumer<T> consumer) {
        return argument -> {
            try{
               consumer.accept(argument);
            }catch(Exception e){
                throw new RuntimeException("Exception occurred at wrapped consumer ", e);
            }
        };
    }

    public static ZoneOffset getZoneOffsetFrom(Instant instant) {
        return seoulZoneId.getRules().getOffset(instant);
    }

}
