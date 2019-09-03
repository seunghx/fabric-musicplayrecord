package com.melon.chaincode.function.v0;

import com.melon.chaincode.function.AbstractChaincodeFunction;
import com.melon.chaincode.util.ResponseUtil;
import com.melon.chaincode.util.ValidationUtil;

import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.List;

public class RegisterMusicPlayRecordChaincodeFunction extends AbstractChaincodeFunction {

    @Override
    protected void validateParameters(List<String> parameters) {
        ValidationUtil.validateParameterLength(parameters, 2);
        ValidationUtil.validateJsonString(parameters.get(1));
    }

    @Override
    protected Chaincode.Response executeChaincode(ChaincodeStub stub, List<String> parameters) {
        String id = parameters.get(0);
        String jsonMusicInfo = parameters.get(1);

        stub.putStringState(id, jsonMusicInfo);
        return ResponseUtil.newSuccessResponse("Register music playing record success.");
    }

}
