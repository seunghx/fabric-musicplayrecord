package com.melon.chaincode.function.v0.query;

import com.melon.chaincode.function.v0.query.AbstractQueryChaincodeFunction;
import com.melon.chaincode.model.query.Query;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class DecoratingQueryChaincodeFunction extends AbstractQueryChaincodeFunction {

    private final AbstractQueryChaincodeFunction chaincodeFunction;

    public DecoratingQueryChaincodeFunction(AbstractQueryChaincodeFunction chaincodeFunction){
        this.chaincodeFunction  = chaincodeFunction;
    }

    @Override
    protected Query makeQuery(List<String> parameters) {
        return chaincodeFunction.makeQuery(parameters);
    }

    @Override
    protected void validateParameters(List<String> parameters) {
       chaincodeFunction.validateParameters(parameters);
    }
}
