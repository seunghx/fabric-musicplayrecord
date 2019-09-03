package com.melon.chaincode.function.v1;

import com.melon.chaincode.function.AbstractChaincodeFunction;
import com.melon.chaincode.util.ResponseUtil;
import com.melon.chaincode.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.List;

@Slf4j
public class AddMusicPlayingRecordFunction extends AbstractChaincodeFunction {

    @Override
    protected void validateParameters(List<String> parameters) {

        log.debug("Validating parameters length");
        ValidationUtil.validateParameterLength(parameters, 2);

        log.debug("Validating key parameter format");
        ValidationUtil.validateDateFormatOnKey(parameters.get(0));

        log.debug("Validating payload music playing record format");
        ValidationUtil.validateJsonString(parameters.get(1));
    }

    @Override
    protected Chaincode.Response executeChaincode(ChaincodeStub stub, List<String> parameters) {
        String key = parameters.get(0);
        String jsonMusicPlayingRecord = parameters.get(1);

        stub.putStringState(key, jsonMusicPlayingRecord);

        log.info("Add music playing record success");
        return ResponseUtil.newSuccessResponse("Add music playing record success");
    }
}
