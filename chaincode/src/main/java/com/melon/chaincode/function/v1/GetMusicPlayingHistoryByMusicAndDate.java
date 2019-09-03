package com.melon.chaincode.function.v1;

import com.melon.chaincode.function.AbstractChaincodeFunction;
import com.melon.chaincode.model.MusicPlayRecordTransaction;
import com.melon.chaincode.util.ResponseUtil;
import com.melon.chaincode.util.Util;
import com.melon.chaincode.util.ValidationUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import static org.hyperledger.fabric.shim.Chaincode.Response;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * TODO :  pagination
 * TODO : cache
 */
@Slf4j
public class GetMusicPlayingHistoryByMusicAndDate extends AbstractChaincodeFunction {

    private static int CACHED_PADDING_COUNT = 3;

    @Override
    protected void validateParameters(List<String> parameters) {

        log.debug("Validating parameters length");
        ValidationUtil.validateParameterLength(parameters, 3);

        log.debug("Validating key parameter format");
        ValidationUtil.validateDateFormatOnKey(parameters.get(0));

    }

    @Override
    protected Response executeChaincode(ChaincodeStub stub, List<String> parameters) {
        String musicIdWithDateKey = parameters.get(0);
        int pageSize = Integer.parseInt(parameters.get(1));
        int pageNum = Integer.parseInt(parameters.get(2));

        /* TODO
       return  map.get(musicIdWithDateKey).stream()
                                   .filter(tx-> tx.getPageNum() == pageNum)
                                   .limit(pageSize)
                                   .collect(Collectors.toList());
         // store nextTransactions to cache map => map.put(musicIdWithDateKey, nextTransactions);
        */

        QueryResultsIterator<KeyModification> iterator = stub.getHistoryForKey(musicIdWithDateKey);
        List<MusicPlayRecordTransactionByDate> transactions =
                                getMusicPlayReocrdsFromKeyModificationLogs(iterator, pageSize, pageNum);

        List<MusicPlayRecordTransactionByDate> requestedTransactions = transactions.subList(0, pageSize);
        List<MusicPlayRecordTransactionByDate> nextTransactions = transactions.subList(pageSize, transactions.size());


        //TODO store nextTransactions to cache ex) map => map.put(musicIdWithDateKey, nextTransactions)

        return getResponseContaining(requestedTransactions);
    }

    private Response getResponseContaining(List<MusicPlayRecordTransactionByDate> transactions){

        return ResponseUtil.newSuccessResponse(Util.jsonRepresentationAsString(transactions));
    }

    //TODO pagination
    //TODO caching

    private List<MusicPlayRecordTransactionByDate> getMusicPlayReocrdsFromKeyModificationLogs(
                                                                        Iterable<KeyModification> iterabble,
                                                                        int pageSize, int pageNum){

        return StreamSupport.stream(iterabble.spliterator(), false)
                            .skip(pageNum * pageSize)
                            .limit(pageSize * CACHED_PADDING_COUNT)
                            .map(MusicPlayRecordTransactionByDate::new)
                            .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    private static class MusicPlayRecordTransactionByDate {
        private String transactionId;
        private String musicPlayRecord;
        private LocalDateTime transactionDateTime;


        public MusicPlayRecordTransactionByDate(KeyModification keyModification){
            this.musicPlayRecord = keyModification.getStringValue();
            this.transactionId = keyModification.getTxId();
            this.transactionDateTime =  transactionDateTimeFrom(keyModification.getTimestamp());
        }

        private LocalDateTime transactionDateTimeFrom(Instant instant){
            return LocalDateTime.ofInstant(instant, Util.getZoneOffsetFrom(instant));
        }
    }

    @Getter
    @Setter
    @ToString
    private static class CachedMusicPlayRecordTransaction {
        private int pageNum;
        private int indexInPageNum;
        private MusicPlayRecordTransaction transaction;
    }
}
