package com.melon.musicplay.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MusicPlayTransaction{

    private String transactionId;
    @JsonProperty("transactionDateTime")
    private String transactionDateTimeFormat;

    @JsonIgnore
    public static final MusicPlayTransaction EMPTY_MUSIC_PLAY_TRANSACTION;

    static {
        EMPTY_MUSIC_PLAY_TRANSACTION = new MusicPlayTransaction();
        EMPTY_MUSIC_PLAY_TRANSACTION.transactionId = "";
    }
}