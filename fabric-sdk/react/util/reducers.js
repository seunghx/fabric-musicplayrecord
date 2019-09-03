import constants from '../constants'
import {appSettings} from '../settings'

export const musics = (state = [], action) => {
    switch (action.type) {
        case constants.LOAD_MUSICS :
            return action.musics
        default:
            return state
    }
}

export const page = (state = 1, action) => {
    switch(action.type){
        case constants.LOAD_MUSICS :
            return action.page
        default :
            return state
    }
}

export const date = (state=new Date(), action) => {
    switch(action.type){
        case constants.DATE_CHANGE :
            return action.date       
        case constants.PAGE_MOVE :
            return new Date()
        default :
            return state
    }
}

export const searchKeyword = (state='', action) => {
    switch(action.type){
        case constants.SEARCH_KEYWORD_CHANGE :
            return action.searchKeyword
        case constants.PAGE_MOVE :
            return ''
        default :
            return state
    }
}

export const searchType = (state=constants.SEARCH_BY_MUSIC, action) => {
    switch(action.type){
        case constants.SEARCH_TYPE_CHANGE :
            return action.searchType
        case constants.PAGE_MOVE : 
            return constants.SEARCH_BY_MUSIC
        default :
            return state
    }
}

export const searchResults = (state=[], action) => {
    switch(action.type){
        case constants.CHANGE_MUSIC_PLAY_RECORDS :
            if(action.searchResults.length === 0 && action.searchActionOrigin !== constants.SEARCH_BUTTON_CLICK) 
                return state
            return action.searchResults
        case constants.PAGE_MOVE :
            return []
        default :
            return state
    }
}

export const withDate = (state=false, action) => {
    switch(action.type){
        case constants.SEARCH_TYPE_CHANGE :
            if(action.searchType === constants.SEARCH_BY_MUSIC_WITH_DATE){
                return true
            }else if(action.searchType === constants.SEARCH_BY_USER_WITH_DATE){
                return true
            }
            return false
        default :
            return state
    }
}

export const currentRecordsPage = (state=0, action) => {
    switch(action.type){         
        case constants.CHANGE_PAGINATION_STATE :
            return action.currentRecordsPage
        default :
            return state
    }
}

export const bookmarks = (state=[], action) => {
    switch(action.type){
        case constants.CHANGE_PAGINATION_STATE :
            return action.bookmarks
        default :
            return state
    }
}

export const nextButtonDisabled = (state= true, action) => {
    switch(action.type){
        case constants.CHANGE_MUSIC_PLAY_RECORDS :
            let resultLength = action.searchResults.length
            if(resultLength === 0 || resultLength < appSettings.PAGE_SIZE)
                 return true
            return false
        default :
            return state
    }
}

export const previousButtonDisabled = (state = true, action) => {
    switch(action.type){
        case constants.CHANGE_PAGINATION_STATE :
            if(action.currentRecordsPage === 0)
                return true
            return false
        default :
            return state
    }
}

export const transactionInfo = (state = {}, action) => {
    switch(action.type){
        case constants.CHANGE_TRANSACTION_INFO : 
            return action.transactionInfo
        default :
            return state
    }
}

export const isModalShow = (state = false, action) => {
    switch(action.type){
        case constants.CHANGE_MODAL_SHOW :
            return action.isModalShow
        default :
            return state
    }
}