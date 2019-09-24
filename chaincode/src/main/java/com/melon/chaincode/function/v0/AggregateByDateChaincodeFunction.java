package com.melon.chaincode.function.v0;

import com.melon.chaincode.function.AbstractChaincodeFunction;
import com.melon.chaincode.function.v0.query.WithDateQueryChaincodeFunction;
import com.melon.chaincode.model.query.Query;
import com.melon.chaincode.util.ResponseUtil;
import com.melon.chaincode.util.Util;
import com.melon.chaincode.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.shim.Chaincode.Response;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
public class AggregateByDateChaincodeFunction extends AbstractChaincodeFunction {

    private final WithDateQueryChaincodeFunction withDateQueryChaincodeFunction;

    public AggregateByDateChaincodeFunction(WithDateQueryChaincodeFunction withDateQueryChaincodeFunction){
        this.withDateQueryChaincodeFunction = withDateQueryChaincodeFunction;
    }

    @Override
    protected Response executeChaincode(ChaincodeStub stub, List<String> parameters){

        /*
         * In a real scenario, there is no need to aggregate query results by user id
         * but in order to make the rehearsal cases more diverse,
         * this variable used for both search by music id and user id
         */
        String aggregationKey = getAggregationKeyFrom(parameters);
        byte[] aggregatedData = stub.getState(aggregationKey);

        if(isAggregatedDataExists(aggregatedData)){
           return ResponseUtil.newSuccessResponse(aggregatedData.toString());
        }else{
            List<String> queryParams = getQueryParamsFrom(aggregationKey);

            QueryResultsIterator<KeyValue> queryResults = getQueryResults(stub, queryParams);

            String totalCount = String.valueOf(aggregateTotalCount(queryResults));

            stub.putStringState(aggregationKey, totalCount);
            return ResponseUtil.newSuccessResponse(totalCount);
        }
    }

    private List<String> getQueryParamsFrom(String key){
        return Arrays.asList(key.split(":"));
    }

    private String getAggregationKeyFrom(List<String> parameters){
        return parameters.get(0);
    }

    private QueryResultsIterator<KeyValue> getQueryResults(ChaincodeStub stub, List<String> parameters) {
        Query query = withDateQueryChaincodeFunction.makeQuery(parameters);
        log.error("###### query : {}", query);
        return stub.getQueryResult(Util.jsonRepresentationAsString(query));
    }

    private long aggregateTotalCount(Iterable<KeyValue> iterable){
        long result =  StreamSupport.stream(iterable.spliterator(), true)
                            .count();

        log.error("######## Result count : {}", result);

        return result;
    }


    private boolean isAggregatedDataExists(byte[] aggregatedData){
        return aggregatedData.length != 0;
    }

    @Override
    protected void validateParameters(List<String> parameters) {
        ValidationUtil.validateParameterLength(parameters, 1);
        ValidationUtil.validateDateFormatOnKey(parameters.get(0));
    }

}
