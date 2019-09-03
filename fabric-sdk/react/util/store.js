import initialStates from '../initialStates'
import {createStore, combineReducers} from 'redux'
import {
    musics, 
    page, 
    date,
    searchKeyword,
    searchType,
    searchResults,
    withDate,
    currentRecordsPage,
    bookmarks,
    nextButtonDisabled,
    previousButtonDisabled,
    isModalShow,
    transactionInfo,
} from './reducers'

const store = createStore(
    combineReducers({
        musics, page, date, searchKeyword, searchType, searchResults, withDate, currentRecordsPage, 
        bookmarks, nextButtonDisabled, previousButtonDisabled, isModalShow, transactionInfo
    }), initialStates)
    
export default store
