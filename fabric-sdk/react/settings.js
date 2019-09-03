const DEFAULT_URL = 'http://localhost:8081' 
const MUSIC_PLAY_RECORDS_URL = DEFAULT_URL + '/music-play-records'

export const apiUrlSettings = {
    MUSIC_PLAY_RECORDS_URL : MUSIC_PLAY_RECORDS_URL,
    MUSIC_PLAY_RECORDS_SEARCH_URL : MUSIC_PLAY_RECORDS_URL + '/search',
    TRANSACTION : "/transaction"
}

export const apiParamNameSettings = {
    SEARCH_KEYWORD : "searchKeyword",
    SEARCH_TYPE : "searchType",
    DATE_PARAM : "date",
    USER_ID_PARAM : "userId",
    MUSIC_ID_PARAM : "musicId",
    PAGE_SIZE_PARAM : "pageSize",
    BOOKMARK_PARAM : "bookmark",
}

export const apiParamValueSettings = {
    SEARCH_BY_USER : "SEARCH_BY_USER",
    SEARCH_BY_MUSIC: "SEARCH_BY_MUSIC",
    SEARCH_BY_USER_WITH_DATE : "SEARCH_BY_USER_WITH_DATE",
    SEARCH_BY_MUSIC_WITH_DATE : "SEARCH_BY_MUSIC_WITH_DATE"
}

export const appSettings = {
    DATE_FORMAT : 'yyyy-MM-dd',
    PAGE_SIZE : 5
}