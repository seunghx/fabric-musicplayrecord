import {connect} from 'react-redux'
import Search from '../components/search'
import MusicPlayers from '../components/musicPlayers'
import {changeDate, changeKeyword, changeSearchType, clearState, changePaginationState,
        changeMusicPlayRecords, changeTransactionInfo, changeModalShow} from './actionFactory'

export const MusicPlayersContainer = connect(
    state => 
        ({          
            musics : state.musics,
        }),
        null
)(MusicPlayers)

export const SearchContainer = connect(
    state => 
        ({
            date : state.date,
            searchKeyword : state.searchKeyword,
            searchType : state.searchType,
            searchResults : state.searchResults,
            withDate : state.withDate,
            bookmark : state.bookmark,
            currentRecordsPage : state.currentRecordsPage,
            bookmarks : state.bookmarks,
            previousButtonDisabled : state.previousButtonDisabled,
            nextButtonDisabled : state.nextButtonDisabled,
            isModalShow : state.isModalShow,
            transactionInfo : state.transactionInfo
        }),
    dispatch =>
        ({
            dispatchChangeDate(newDate){
                dispatch(changeDate(newDate))
            },
            dispatchChangeKeyword(keyword){
                dispatch(changeKeyword(keyword))
            },
            dispatchChangeSearchType(searchType){
                dispatch(changeSearchType(searchType))
            },
            dispatchClearState(){
                dispatch(clearState())
            },
            dispatchChangePaginationState(bookmarks, currentRecordsPage){
                dispatch(changePaginationState(bookmarks, currentRecordsPage))
            },
            dispatchChangeMusicPlayRecords(searchResults, searchActionOrigin){
                dispatch(changeMusicPlayRecords(searchResults, searchActionOrigin))
            },
            dispatchChangeTransactionInfo(transactionInfo){
                console.log(transactionInfo)
                dispatch(changeTransactionInfo(transactionInfo))
            },
            dispatchChangeModalShow(isModalShow){
                dispatch(changeModalShow(isModalShow))
            }
        })
)(Search)



