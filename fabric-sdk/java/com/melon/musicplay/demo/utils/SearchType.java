package com.melon.musicplay.demo.utils;

public enum SearchType {
    
    SEARCH_BY_USER("SEARCH_BY_USER"),
    SEARCH_BY_MUSIC("SEARCH_BY_MUSIC"),
    SEARCH_BY_USER_WITH_DATE("SEARCH_BY_USER_WITH_DATE"),
    SEARCH_BY_MUSIC_WITH_DATE("SEARCH_BY_MUSIC_WITH_DATE");

    private String searchType;

    private SearchType(String searchType){
        this.searchType = searchType;
    }

    public String getSearchType(){
        return searchType;
    }
}