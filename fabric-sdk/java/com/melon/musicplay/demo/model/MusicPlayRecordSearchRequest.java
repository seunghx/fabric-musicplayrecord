package com.melon.musicplay.demo.model;

import com.melon.musicplay.demo.utils.SearchType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MusicPlayRecordSearchRequest {

    private SearchType searchType;
    private String searchKeyword;
    private String pageSize= "20";
    private String bookmark= "";

    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class MusicPlayRecordSearchRequestWithDate extends MusicPlayRecordSearchRequest{
        private String date;
    }
}

