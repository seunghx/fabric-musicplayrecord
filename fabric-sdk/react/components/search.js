import React from 'react'
import {format} from 'date-fns'
import {withRouter} from 'react-router'
import PageTemplate from '../util/pageTemplate'
import {search} from '../util/api'
import {apiParamNameSettings, appSettings} from '../settings'
import {
    SearchType, 
    SearchKeywordInput, 
    SearchButton, 
    SearchDatePick, 
    SearchResults,
    Pagination,
    TransactionModal
} from './searchView'
import constants from '../constants'
    
const Search = ({
        date, 
        searchKeyword, 
        searchType,
        searchResults=[],
        history,
        withDate,
        bookmarks,
        currentRecordsPage,
        nextButtonDisabled,
        previousButtonDisabled,
        transactionInfo,
        isModalShow,

        dispatchClearState,
        dispatchChangeSearchType,
        dispatchChangeDate, 
        dispatchChangeKeyword,
        dispatchChangePaginationState,
        dispatchChangeMusicPlayRecords,
        dispatchChangeTransactionInfo,
        dispatchChangeModalShow
}) => {
    console.log('>>>')
    console.log(transactionInfo)
    const unlisten = history.listen((location, action) => dispatchClearState())

    const onSearchButtonClick = () => {
        let searchOptionMap = new Map()

        if(withDate){
            searchOptionMap = createSearchOptionMapWithDate(date, searchKeyword, searchType, '')
        }else{
            searchOptionMap = createSearchOptionMap(searchKeyword, searchType, '')
        }

        search(
            searchOptionMap, 
            res => {onSearchResultsSuccess(res, ['', res.data.bookmark], 0, constants.SEARCH_BUTTON_CLICK)},
            err => console.log(err)
        )
    }

    const onNextButtonclick = () => {
        let searchOptionMap = new Map()

        if(withDate){
            searchOptionMap = createSearchOptionMapWithDate(date, searchKeyword, searchType, bookmarks[currentRecordsPage+1])
        }else{
            searchOptionMap = createSearchOptionMap(searchKeyword, searchType, bookmarks[currentRecordsPage+1])
        }

        search(
            searchOptionMap, 
            res => {
                console.log(res.data)
                let newBookmarks = (res.data.musicPlayRecords.length == 0)? [...bookmarks] : [...bookmarks, res.data.bookmark]
                let newRecordsPage = (res.data.musicPlayRecords.length == 0)? currentRecordsPage : currentRecordsPage + 1
                onSearchResultsSuccess(res, newBookmarks, newRecordsPage, constants.NEXT_BUTTON_CLICK)
            },
            err => console.log(err)
        )
    }

    const onPreviousButtonClick = () => {
        let searchOptionMap = new Map()

        if(withDate){
            searchOptionMap = createSearchOptionMapWithDate(date, searchKeyword, searchType, bookmarks[currentRecordsPage-1])
        }else{
            searchOptionMap = createSearchOptionMap(searchKeyword, searchType, bookmarks[currentRecordsPage-1])
        }

        search(
            searchOptionMap, 
            res => onSearchResultsSuccess(res, [...bookmarks.slice(0, currentRecordsPage), res.data.bookmark], 
                                          currentRecordsPage - 1, constants.PREVIOUS_BUTTON_CLICK),
            err => console.log(err)
        )
    }

    const createSearchOptionMap = (searchKeyword, searchType, bookmark) => {
        let searchOptionMap = new Map();
        searchOptionMap.set(apiParamNameSettings.SEARCH_KEYWORD, searchKeyword)
        searchOptionMap.set(apiParamNameSettings.SEARCH_TYPE, searchType)

        searchOptionMap.set(apiParamNameSettings.BOOKMARK_PARAM, bookmark)
        searchOptionMap.set(apiParamNameSettings.PAGE_SIZE_PARAM, appSettings.PAGE_SIZE)

        return searchOptionMap
    }

    const createSearchOptionMapWithDate = (date, searchKeyword, searchType, bookmark) => {
        let searchOptionMap = createSearchOptionMap(searchKeyword, searchType, bookmark)
        searchOptionMap.set(apiParamNameSettings.DATE_PARAM, format(date, appSettings.DATE_FORMAT))
    
        return searchOptionMap
    }

    const onSearchResultsSuccess = (res, bookmarks, currentRecordsPage, searchActionOrigin) => {
        dispatchChangeMusicPlayRecords(res, searchActionOrigin)
        dispatchChangePaginationState(bookmarks, currentRecordsPage)
    }

    return(
        <PageTemplate>
            <SearchType dispatchChangeSearchType={searchType => dispatchChangeSearchType(searchType)} searchType={searchType}/>
            {(withDate)?<SearchDatePick dispatchChangeDate={newDate => {dispatchChangeDate(newDate)}} date={date} /> : ''}
            <SearchKeywordInput searchKeyword={searchKeyword} 
                        dispatchChangeKeyword={keyword => dispatchChangeKeyword(keyword)}/>
            <SearchButton onSearchButtonClick={onSearchButtonClick}/>
            <SearchResults searchResults={searchResults} 
                            dispatchChangeTransactionInfo={dispatchChangeTransactionInfo} 
                            dispatchChangeModalShow={dispatchChangeModalShow}/>
            <Pagination previousButtonDisabled={previousButtonDisabled} nextButtonDisabled={nextButtonDisabled}
                        onNextButtonclick={onNextButtonclick} onPreviousButtonClick={onPreviousButtonClick}  />
            <TransactionModal isModalShow={isModalShow} transactionInfo={transactionInfo} 
                            dispatchChangeModalShow={dispatchChangeModalShow} /> 

        </PageTemplate>
    )
}

export default withRouter(Search)
