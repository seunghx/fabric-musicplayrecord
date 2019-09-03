package com.melon.chaincode.function;

import org.hyperledger.fabric.shim.Chaincode.Response;
import org.hyperledger.fabric.shim.ChaincodeStub;

public interface ChaincodeFunction {
    Response execute(ChaincodeStub stub);
}

