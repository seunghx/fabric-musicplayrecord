package com.melon.musicplay.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AggregatedMusicTransactionInfo {
    private long totalCount;

    public AggregatedMusicTransactionInfo(long totalCount){
        this.totalCount = totalCount;
    }

    public static final AggregatedMusicTransactionInfo EMPTY_AGGREGATED_RESULT 
                                                = new AggregatedMusicTransactionInfo(0);
}