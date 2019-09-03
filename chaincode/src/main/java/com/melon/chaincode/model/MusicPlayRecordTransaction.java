package com.melon.chaincode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
@ToString
public class MusicPlayRecordTransaction {
    private static final Logger log = LoggerFactory.getLogger(MusicPlayRecordTransaction.class);
    private String transactionId;
    private LocalDateTime transactionDateTime;

    public MusicPlayRecordTransaction(String transactionId, LocalDateTime transactionDateTime){
        log.error("Transaction id : {}", transactionId);
        log.error("Transaction date time : {}", transactionDateTime);

        this.transactionId = transactionId;
        this.transactionDateTime = transactionDateTime;
    }

    public static MusicPlayRecordTransaction EMPTY_TRANSACTION =
                new MusicPlayRecordTransaction("No transaction for requested key", LocalDateTime.now());

}
