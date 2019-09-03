package com.melon.musicplay.demo.model;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MusicPlayRecordResponse {
    private List<MusicPlayRecord> musicPlayRecords;
    private String bookmark;

    @JsonIgnore
    public static final MusicPlayRecordResponse EMPTY_MUSIC_PLAY_RESPONSE;

    static {
        EMPTY_MUSIC_PLAY_RESPONSE = new MusicPlayRecordResponse();
        EMPTY_MUSIC_PLAY_RESPONSE.bookmark = "";
        EMPTY_MUSIC_PLAY_RESPONSE.musicPlayRecords = new ArrayList<>();
    }
}