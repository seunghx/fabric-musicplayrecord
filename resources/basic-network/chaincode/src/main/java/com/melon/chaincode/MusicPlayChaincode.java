package com.melon.chaincode;

import com.melon.chaincode.function.ChaincodeFunction;
import com.melon.chaincode.util.ChaincodeFunctionFactory;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

@Slf4j
public class MusicPlayChaincode extends ChaincodeBase {

    @Override
    public Response init(ChaincodeStub stub) {
        log.info("Chaincode initialization is completed.");
        return newSuccessResponse();
    }

    @Override
    public Response invoke(ChaincodeStub stub) {

        String functionName = getFunctionName(stub);

        log.info("Invoked chaincode function name : {}", functionName);

        ChaincodeFunction function = ChaincodeFunctionFactory.getChaincodeFunction(functionName);

        try{
            return function.execute(stub);
        }catch(Exception e){
            return newErrorResponse(e.toString());
        }
    }


    private String getFunctionName(ChaincodeStub stub){
        return stub.getFunction();
    }

    public static void main(String[] args) {
        new MusicPlayChaincode().start(args);
    }

}
