import axios from 'axios';
import {apiUrlSettings} from '../settings'

export const playMusic = (musicInfo, apiCallback, errCallback) => {    
    postCall(apiUrlSettings.MUSIC_PLAY_RECORDS_URL, musicInfo, apiCallback, errCallback)
}

export const search = (searchOptionMap, apiCallback, errCallback) => {
    let url = apiUrlSettings.MUSIC_PLAY_RECORDS_SEARCH_URL + paramsToUrlQuery(searchOptionMap)
    getCall(url, apiCallback, errCallback)
}

export const getTransactionForMusicPlayRecord = (musicPlayRecordKey, apiCallback, errCallback) => {
    let url = apiUrlSettings.MUSIC_PLAY_RECORDS_URL + apiUrlSettings.TRANSACTION + "/" + musicPlayRecordKey

    getCall(url, apiCallback, errCallback)
}

const postCall = (url, data, apiCallback, errCallback) => {
    axios.post(url, data)
         .then(apiCallback)
         .catch(errCallback)
}

const getCall = (url, apiCallback, errCallback) => {
    axios.get(url)
         .then(apiCallback)
         .catch(errCallback)
}

const paramsToUrlQuery = (searchOptionMap) => {
    let queryString = ''

    for(let [key, value] of searchOptionMap){
        queryString += '&&' + key + '=' + value
    }
    return '?' + queryString.slice(2)
 }

