import React from 'react'
import DatePicker from 'react-datepicker'
import constants from '../constants'
import {getTransactionForMusicPlayRecord} from '../util/api'
import { Modal } from 'react-bootstrap';


export const SearchType = ({dispatchChangeSearchType, searchType=constants.SEARCH_BY_MUSIC}) => 
    <div className="col-lg-3 col-md-3 search-element">
        <select className="search-slt" onChange={dispatchChangeSearchType} value={searchType}>
            <option value={constants.SEARCH_BY_MUSIC} >Search By</option>
            <option value={constants.SEARCH_BY_MUSIC} >Music</option>
            <option value={constants.SEARCH_BY_USER}>User</option>
            <option value={constants.SEARCH_BY_MUSIC_WITH_DATE}>Music with date</option>
            <option value={constants.SEARCH_BY_USER_WITH_DATE}>User with date</option>
        </select>
    </div>


export const SearchKeywordInput = ({searchKeyword, dispatchChangeKeyword}) => 
    <div className="col-lg-3 col-md-3 search-element ">
        <input type="text" className="search-slt" placeholder="Search" value={searchKeyword}
            onChange={dispatchChangeKeyword}/>
    </div>

export const SearchButton = ({onSearchButtonClick}) => 
    <div className="col-lg-3 col-md-3 search-btn search-element">
        <button type="button" className="btn wrn-btn" onClick={onSearchButtonClick}>
            Search
        </button>
    </div>

export const SearchDatePick = ({date, dispatchChangeDate}) =>{
    return(
    <div className="col-lg-3 col-md-3 search-element">
        <DatePicker onChange={dispatchChangeDate} selected={date} dateFormat="yy-MM-dd" 
            placeholderText="Select a date for searching" maxDate={new Date()} disabledKeyboardNavigation 
            showMonthDropdown showYearDropdown dropdownMode="select" useShortMonthInDropdown
        />
    </div>
    )
}

export const SearchResults = ({searchResults, dispatchChangeTransactionInfo, dispatchChangeModalShow}) => 
    <div className="row">
        <table className="table table-hover">
            <thead>
                <tr>
                    <th scope="col">NO</th>
                    <th scope="col">title</th>
                    <th scope="col">singer</th>
                    <th scope="col">album</th>
                    <th scope="col">user</th>
                    <th scope="col">listen date time</th>
                </tr>
            </thead>
            <tbody>
                {searchResults.map((searchResult,index) => 
                    <SearchResult searchResult={searchResult} index={index} key={index}
                    dispatchChangeTransactionInfo={dispatchChangeTransactionInfo} 
                    dispatchChangeModalShow={dispatchChangeModalShow}/>)}
            </tbody>
        </table>
    </div>
    
 

export const SearchResult = ({searchResult, index, dispatchChangeTransactionInfo, dispatchChangeModalShow}) => {
    const apiCallback = res => {
        console.log(res.data)
        dispatchChangeTransactionInfo(res.data)
        dispatchChangeModalShow(true)
    }

    const onRowClicked = () => {
        getTransactionForMusicPlayRecord(searchResult.key, res => apiCallback(res), err => console.log(err))
    }

    return(
        <tr onClick={onRowClicked} id="searchResultRow">
            <th scope="row" >{index+1}</th>
            <td>{searchResult.musicInfo.title}</td>
            <td>{[...searchResult.musicInfo.singers]}</td>
            <td>{searchResult.musicInfo.album}</td>
            <td>{searchResult.userId}</td>
            <td>{searchResult.listenDateTime}</td>
        </tr>
    )
}

export const Pagination = ({previousButtonDisabled, nextButtonDisabled, onNextButtonclick, onPreviousButtonClick}) => {
    let previousBtnDisabledClass = (previousButtonDisabled)?' disabled':''
    let nextBtnDisabledClass = (nextButtonDisabled)?' disabled':''

    return(
    <ul className="pagination button-center">
        <li className={"page-item" + previousBtnDisabledClass} >
            <button className="btn page-link"  href="#" disabled={previousButtonDisabled} onClick={onPreviousButtonClick}>Previous</button>
        </li>
        <li className={"page-item" + nextBtnDisabledClass}>
            <button className="btn page-link"  href="#" disabled={nextButtonDisabled} onClick={onNextButtonclick}>Next</button>
        </li>
    </ul>
    )
}

export const TransactionModal = ({isModalShow, transactionInfo, dispatchChangeModalShow}) => {
    console.log(transactionInfo)

    return(
    <Modal centered size="lg" show={isModalShow} onHide={e => dispatchChangeModalShow(false)}>
        <Modal.Header closeButton>
            <Modal.Title>Transaction Info</Modal.Title>
        </Modal.Header>
        <Modal.Body>ID : {transactionInfo.transactionId}</Modal.Body>
        <Modal.Body>CreatedAt : {transactionInfo.transactionDateTime}</Modal.Body>
        <Modal.Footer>use transaction id for validation</Modal.Footer>
    </Modal>
    )
}


