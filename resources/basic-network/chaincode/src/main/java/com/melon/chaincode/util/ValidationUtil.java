package com.melon.chaincode.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ValidationUtil {

    private static final Logger logger  = LoggerFactory.getLogger(ValidationUtil.class);

    private static final String NULL_PARAMETER_MESSAGE_FORMAT = "Invalid parameter [name: %s, value: null]";
    private static final String INVALID_PARAMETER_SIZE_MESSAGE_FORMAT = "Invalid parameter size. Expected : %s but %s";
    private static final String INVALID_JSON_MESSAGE_FORMAT = "Invalid json parameter : %s";
    private static final String INVALID_DATE_FORMAT = "Invalid date format : %s";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static void validateParameterLength(List<String> chaincodeParameters, int size){
        Objects.requireNonNull(chaincodeParameters,
                               ()-> String.format(NULL_PARAMETER_MESSAGE_FORMAT, "chaincodeParameters"));

        if(chaincodeParameters.size() != size){

            throw new IllegalArgumentException(String.format(INVALID_PARAMETER_SIZE_MESSAGE_FORMAT,
                                                                size, chaincodeParameters.size()));
        }
    }

    public static void validateJsonString(String jsonString) {
        try{
            objectMapper.readTree(jsonString);
        }catch(Exception e){
            throw new IllegalArgumentException(String.format(INVALID_JSON_MESSAGE_FORMAT, jsonString));
        }
    }

    public static void validateDateFormatOnKey(String key){
       String dateFormat = key.split(":")[1];

       try{
           LocalDate.parse(dateFormat, dateFormatter);
       }catch(Exception e){
           throw new IllegalArgumentException(String.format(INVALID_DATE_FORMAT));
       }
    }
}
