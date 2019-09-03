package com.melon.chaincode.util;

import com.melon.chaincode.function.v0.GetTransactionByKeyFunction;
import com.melon.chaincode.function.v0.RegisterMusicPlayRecordChaincodeFunction;
import com.melon.chaincode.function.ChaincodeFunction;
import com.melon.chaincode.function.v0.query.GetMusicPlayRecordByMusicChaincodeFunction;
import com.melon.chaincode.function.v0.query.GetMusicPlayRecordByUserChaincodeFunction;
import com.melon.chaincode.function.v0.query.WithDateQueryChaincodeFunction;
import com.melon.chaincode.function.v1.AddMusicPlayingRecordFunction;
import com.melon.chaincode.function.v1.GetMusicPlayingHistoryByMusicAndDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChaincodeFunctionFactory {

    /*************************************** chaincode version 1 *******************************************/

    private static RegisterMusicPlayRecordChaincodeFunction registerMusicPlayRecordFunction =
                                                new RegisterMusicPlayRecordChaincodeFunction();
    private static GetMusicPlayRecordByMusicChaincodeFunction getMusicPlayRecordByMusicFunction =
                                                new GetMusicPlayRecordByMusicChaincodeFunction();
    private static GetMusicPlayRecordByUserChaincodeFunction getMusicPlayRecordByUserFunction =
                                                new GetMusicPlayRecordByUserChaincodeFunction();
    private static GetTransactionByKeyFunction getTransactionByKeyFunction =
                                                new GetTransactionByKeyFunction();

    /*************************************** chaincode version 2 *******************************************/

    private static AddMusicPlayingRecordFunction addMusicPlayingRecordFunction =
                                                new AddMusicPlayingRecordFunction();
    private static GetMusicPlayingHistoryByMusicAndDate getMusicPlayingHistoryByMusicAndDate =
                                                new GetMusicPlayingHistoryByMusicAndDate();

    public static ChaincodeFunction getChaincodeFunction(String functionName){

        /*************************************** chaincode version 1 *******************************************/

        if("registerMusicPlayRecord".equals(functionName)){
            return registerMusicPlayRecordFunction;
        }else if("getMusicPlayRecordByMusic".equals(functionName)) {
            return getMusicPlayRecordByMusicFunction;
        }else if("getMusicPlayRecordByUser".equals(functionName)){
            return getMusicPlayRecordByUserFunction;
        }else if("getMusicPlayRecordByMusicAndDate".equals(functionName)){
            return new WithDateQueryChaincodeFunction(getMusicPlayRecordByMusicFunction);
        }else if("getMusicPlayRecordByUserAndDate".equals(functionName)) {
            return new WithDateQueryChaincodeFunction(getMusicPlayRecordByUserFunction);
        } else if ("getTransactionRecordByKey".equals(functionName)) {
            return getTransactionByKeyFunction;

        /*************************************** chaincode version 2 *******************************************/

        }else if("addMusicPlayingRecord".equals(functionName)){
            return addMusicPlayingRecordFunction;
        }else if("getMusicPlayingHistoryByMusicAndDate".equals(functionName)){
            return getMusicPlayingHistoryByMusicAndDate;
        }

        throw new IllegalArgumentException("Unknown function name.");
    }
}
