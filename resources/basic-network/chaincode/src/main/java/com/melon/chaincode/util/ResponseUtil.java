package com.melon.chaincode.util;

import org.hyperledger.fabric.shim.Chaincode;
import static org.hyperledger.fabric.shim.Chaincode.Response;
import static org.hyperledger.fabric.shim.Chaincode.Response.Status.SUCCESS;


public class ResponseUtil {

    public static Response newSuccessResponse(String message, Object payload){
        return new Response(SUCCESS, message, Util.jsonRepresentationAsByte(payload));
    }

    public static Response newSuccessResponse(String message){
        return newSuccessResponse(message, null);
    }

}
