import constants from '../constants'

export const changeDate = newDate =>
   ({
        type : constants.DATE_CHANGE,
        date : newDate
    })

export const changeKeyword = e =>
    ({
        type : constants.SEARCH_KEYWORD_CHANGE,
        searchKeyword : e.target.value
    })

export const changeSearchType = e => 
    ({
        type : constants.SEARCH_TYPE_CHANGE,
        searchType : e.target.value
    })

export const changeMusicPlayRecords = (searchResults, searchActionOrigin) => 
    ({
        type : constants.CHANGE_MUSIC_PLAY_RECORDS,
        searchResults : searchResults.data.musicPlayRecords,
        searchActionOrigin : searchActionOrigin
     })

export const changePaginationState = (bookmarks, currentRecordsPage) => 
     ({
        type : constants.CHANGE_PAGINATION_STATE,
        bookmarks : bookmarks,
        currentRecordsPage : currentRecordsPage,
     })

export const clearState = () =>
    ({
        type : constants.PAGE_MOVE
    })

export const changeTransactionInfo = (transactionInfo) =>
    ({
        type : constants.CHANGE_TRANSACTION_INFO,
        transactionInfo : transactionInfo
    })

export const changeModalShow = (isModalShow) =>
    ({
        type : constants.CHANGE_MODAL_SHOW,
        isModalShow : isModalShow
    })