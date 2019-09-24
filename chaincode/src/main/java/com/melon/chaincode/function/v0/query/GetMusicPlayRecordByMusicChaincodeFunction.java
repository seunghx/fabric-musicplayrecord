package com.melon.chaincode.function.v0.query;

import com.melon.chaincode.model.query.Query;
import com.melon.chaincode.model.query.Query.QueryBuilder;
import com.melon.chaincode.model.query.SortDirection;
import com.melon.chaincode.util.ValidationUtil;

import java.util.List;

public class GetMusicPlayRecordByMusicChaincodeFunction extends AbstractQueryChaincodeFunction {

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
        String musicId = getMusicIdFrom(parameters);

        return QueryBuilder.newQueryBuilder(MUSIC_DOC_TYPE)
                           .selector("musicInfo.musicId", musicId)
                            .indexing("_design/indexListenDateTimeDoc", "indexListenDateTime")
                           .sortBy("listenDateTime", SortDirection.DESC)
                           .buildQuery();
    }

    private String getMusicIdFrom(List<String> parameters){
        return parameters.get(0);
    }

}
