package com.melon.musicplay.demo;

import com.melon.musicplay.demo.model.AggregatedMusicTransactionInfo;
import com.melon.musicplay.demo.model.MusicInfo;
import com.melon.musicplay.demo.model.MusicPlayRecordResponse;
import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest;
import com.melon.musicplay.demo.model.MusicPlayTransaction;
import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest.MusicPlayRecordSearchRequestWithDate;
import com.melon.musicplay.demo.service.MusicPlayRecordBlockChainService;
import com.melon.musicplay.demo.utils.SearchType;

import static com.melon.musicplay.demo.utils.SearchType.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@Slf4j
public class MusicPlayRecordController {

    private static final String MUSIC_PLAY_RECORDS_PATH = "/music-play-records";

    private final MusicPlayRecordBlockChainService musicPlayRecordService;

    public MusicPlayRecordController(MusicPlayRecordBlockChainService musicPlayRecordService){
        this.musicPlayRecordService = musicPlayRecordService;
    }

    @GetMapping("/")
    public String page( Model model){
        return "page";
    }

    @PostMapping(MUSIC_PLAY_RECORDS_PATH)
    public ResponseEntity<Void> playMusic(@RequestBody MusicInfo musicInfo){
        String userId = "testUser";

        musicPlayRecordService.addMusicPlayRecord(musicInfo, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(MUSIC_PLAY_RECORDS_PATH + "/search")
    public ResponseEntity<MusicPlayRecordResponse> getMusicPlayRecord(MusicPlayRecordSearchRequest searchRequest){
        MusicPlayRecordResponse records = null;

        log.debug("Request : {}", searchRequest);

        if(SEARCH_BY_MUSIC == searchRequest.getSearchType()){
            records = musicPlayRecordService.getMusicPlayRecordByMusicId(searchRequest);
        }else if(SEARCH_BY_MUSIC_WITH_DATE == searchRequest.getSearchType()){
            records = musicPlayRecordService.getMusicPlayRecordByMusicIdAndDate(
                        (MusicPlayRecordSearchRequestWithDate) searchRequest);
        }else if(SEARCH_BY_USER == searchRequest.getSearchType()){
            records = musicPlayRecordService.getMusicPlayRecordByUserId(searchRequest);
        }else if(SEARCH_BY_USER_WITH_DATE == searchRequest.getSearchType()){
            records = musicPlayRecordService.getMusicPlayRecordByUserIdAndDate(
                        (MusicPlayRecordSearchRequestWithDate) searchRequest);
        }

        log.error("============================================");
        log.error("Retrieved music play records : {}", records);
        log.error("============================================");

        return newOKResponse(records);
    }

    @GetMapping(MUSIC_PLAY_RECORDS_PATH + "/transaction/{musicPlayRecordKey}")
    public ResponseEntity<MusicPlayTransaction> getTransactionForMusicPlayRecord(
                                                    @PathVariable("musicPlayRecordKey") String musicPlayRecordKey){
                                                        
        MusicPlayTransaction transactionInfo = musicPlayRecordService.getMusicPlayTransaction(musicPlayRecordKey);

        log.error("============================================");
        log.error("Retrieved transaction info : {}", transactionInfo);
        log.error("============================================");

        return newOKResponse(transactionInfo);
    }

    @GetMapping(MUSIC_PLAY_RECORDS_PATH + "/aggregation")
    public ResponseEntity<AggregatedMusicTransactionInfo> getAggregatedMusicTransactionInfo(
                                                        @RequestParam("key") String aggregationKey,
                                                        @RequestParam("searchType") String searchRequestType){
        SearchType searchType = SearchType.valueOf(searchRequestType);

        if(searchType == SEARCH_BY_MUSIC_WITH_DATE){
            return newOKResponse(musicPlayRecordService.getAggregatedMusicTransactionInfoByMusic(aggregationKey));
        }else if(searchType == SEARCH_BY_USER_WITH_DATE){
            return newOKResponse(musicPlayRecordService.getAggregatedMusicTransactionInfoByUser(aggregationKey));
        }else{
            throw new IllegalArgumentException("Unsupported search type for aggregation");
        }
    }

    private <T> ResponseEntity<T> newOKResponse(T payload){
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

}