package com.melon.chaincode.model.query;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SortDirection {
    ASC("asc"), DESC("desc");

    private String direction;

    SortDirection(String direction){
        this.direction = direction;
    }

    @JsonValue
    public String getDirection(){
        return direction;
    }
}
