package com.melon.chaincode.function.v0.query;

import com.melon.chaincode.function.v0.query.AbstractQueryChaincodeFunction;
import com.melon.chaincode.model.query.Query;
import com.melon.chaincode.model.query.Query.QueryBuilder;
import com.melon.chaincode.model.query.SortDirection;
import com.melon.chaincode.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GetMusicPlayRecordByUserChaincodeFunction extends AbstractQueryChaincodeFunction {

    @Override
    protected void validateParameters(List<String> parameters) {
        ValidationUtil.validateParameterLength(parameters, 3);
    }

    @Override
    protected Query makeQuery(List<String> parameters) {
        /**
         *  Because of chaincode api spec that receives parameters as String List,
         *  it is required to explicitly assign parameter's order(or index location)
         */
        String userId = parameters.get(0);

        return QueryBuilder.newQueryBuilder(MUSIC_DOC_TYPE)
                           .selector("userId", userId)
                           .indexing("_design/indexListenDateTimeDoc", "indexListenDateTime")
                           .sortBy("listenDateTime", SortDirection.DESC)
                           .buildQuery();
    }
}
