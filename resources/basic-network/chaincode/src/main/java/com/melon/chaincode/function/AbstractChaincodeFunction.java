package com.melon.chaincode.function;

import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.util.List;

@Slf4j
public abstract class AbstractChaincodeFunction implements ChaincodeFunction {

    @Override
    public Chaincode.Response execute(ChaincodeStub stub) {
        List<String> parameters = stub.getParameters();
        validateParameters(parameters);

        log.info("Validatiing chaincode parameter success.");

        return executeChaincode(stub, parameters);
    }

    protected abstract void validateParameters(List<String> parameters);
    protected abstract Chaincode.Response executeChaincode(ChaincodeStub stub, List<String> parameters);

}
