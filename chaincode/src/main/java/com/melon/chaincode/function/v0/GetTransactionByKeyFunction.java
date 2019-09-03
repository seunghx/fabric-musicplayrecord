package com.melon.chaincode.function.v0;

import com.melon.chaincode.function.AbstractChaincodeFunction;
import com.melon.chaincode.model.MusicPlayRecordTransaction;
import com.melon.chaincode.util.ResponseUtil;
import com.melon.chaincode.util.Util;
import com.melon.chaincode.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import static org.hyperledger.fabric.shim.Chaincode.Response;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class GetTransactionByKeyFunction extends AbstractChaincodeFunction {

    @Override
    protected void validateParameters(List<String> parameters) {
        ValidationUtil.validateParameterLength(parameters, 1);
    }

    @Override
    protected Response executeChaincode(ChaincodeStub stub, List<String> parameters) {

        String musicPlayRecordKey = parameters.get(0);

        QueryResultsIterator<KeyModification> result = stub.getHistoryForKey(musicPlayRecordKey);

        return getResponseFromQueryResult(result.iterator());
    }

    private Response getResponseFromQueryResult(Iterator<KeyModification> resultIterator){
        String responseJsonString;

        if(resultIterator.hasNext()){
            // For design reason, results iterator contains only 1 element
            MusicPlayRecordTransaction responseTransaction = getTransactionFrom(resultIterator.next());
            responseJsonString = Util.jsonRepresentationAsString(responseTransaction);
        }else{
            responseJsonString = Util.jsonRepresentationAsString(MusicPlayRecordTransaction.EMPTY_TRANSACTION);
        }

        return getResponseContaining(responseJsonString);
    }

    private MusicPlayRecordTransaction getTransactionFrom(KeyModification keyModification){
        String transactionId = keyModification.getTxId();
        Instant transactionAt = keyModification.getTimestamp();

        return new MusicPlayRecordTransaction(transactionId, getTransactionLocalDateTimeFrom(transactionAt));
    }

    private LocalDateTime getTransactionLocalDateTimeFrom(Instant transactionAt){
        return LocalDateTime.ofInstant(transactionAt, Util.getZoneOffsetFrom(transactionAt));
    }

    private Response getResponseContaining(String jsonResponse){
        return ResponseUtil.newSuccessResponse(jsonResponse);
    }
}
