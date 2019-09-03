package com.melon.chaincode.function.v0.query;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.melon.chaincode.function.AbstractChaincodeFunction;
import com.melon.chaincode.model.query.Query;
import com.melon.chaincode.util.ResponseUtil;
import com.melon.chaincode.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.Chaincode.Response;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIteratorWithMetadata;
import java.util.List;

@Slf4j
public abstract class AbstractQueryChaincodeFunction extends AbstractChaincodeFunction {

    protected static final String MUSIC_DOC_TYPE = "music";

    @Override
    protected Chaincode.Response executeChaincode(ChaincodeStub stub, List<String> parameters) {
        Query query = makeQuery(parameters);
        int pageSize = getPageSizeFrom(parameters);
        String bookmark = getBookmarkFrom(parameters);

        QueryResultsIteratorWithMetadata<KeyValue> resultsIterator =
                stub.getQueryResultWithPagination(Util.jsonRepresentationAsString(query), pageSize, bookmark);

        return getResponseFrom(resultsIterator);
    }

    private int getPageSizeFrom(List<String> parameters){
        return Integer.parseInt(parameters.get(getPageSizeIndex(parameters)));
    }

    /**
     *  Because of fabric chaincode api spec that receives parameters as String List,
     *  it is required to explicitly assign parameter's order(or index location)
     */
    private int getPageSizeIndex(List<String> parameters){
        return parameters.size() - 3;
    }

    private String getBookmarkFrom(List<String> parameters){
        return parameters.get(getBookmarkIndex(parameters));
    }

    /**
     *  Because of fabric chaincode api spec that receives parameters as String List,
     *  it is required to explicitly assign parameter's order(or index location)
     */
    private int getBookmarkIndex(List<String> parameters){
        return parameters.size() - 2;
    }

    private Response getResponseFrom(QueryResultsIteratorWithMetadata<KeyValue> resultsIterator){
        ObjectNode objectNode = convertKeyValuesToObjectNode(resultsIterator);
        objectNode.put("bookmark", resultsIterator.getMetadata().getBookmark());

        return createAndGetJsonResponse(objectNode);
    }

    private ObjectNode convertKeyValuesToObjectNode(Iterable<KeyValue> queryResults){
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        ArrayNode arrayNode = node.withArray("musicPlayRecords");

        queryResults.forEach(kv -> arrayNode.add(Util.readObjectTreeFromJsonString(kv.getStringValue())));

        return node;
    }

    private Response createAndGetJsonResponse(ObjectNode resultNode){

        String response = Util.jsonRepresentationAsString(resultNode);
        return ResponseUtil.newSuccessResponse(response);
    }

    protected abstract Query makeQuery(List<String> parameters);
    protected abstract void validateParameters(List<String> parameters);

}
