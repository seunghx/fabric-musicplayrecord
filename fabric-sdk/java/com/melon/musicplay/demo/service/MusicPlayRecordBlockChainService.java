package com.melon.musicplay.demo.service;

import com.melon.musicplay.demo.model.MusicInfo;
import com.melon.musicplay.demo.model.MusicPlayRecordResponse;
import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest;
import com.melon.musicplay.demo.model.MusicPlayTransaction;
import com.melon.musicplay.demo.model.MusicPlayRecordSearchRequest.MusicPlayRecordSearchRequestWithDate;

public interface MusicPlayRecordBlockChainService {
    
    MusicPlayRecordResponse getMusicPlayRecordByMusicId(MusicPlayRecordSearchRequest searchRequest);
    MusicPlayRecordResponse getMusicPlayRecordByMusicIdAndDate(
                                                MusicPlayRecordSearchRequestWithDate searchRequest);

    MusicPlayRecordResponse getMusicPlayRecordByUserId(MusicPlayRecordSearchRequest searchRequest);
    MusicPlayRecordResponse getMusicPlayRecordByUserIdAndDate(MusicPlayRecordSearchRequestWithDate searchRequest);
    
    void addMusicPlayRecord(MusicInfo musicInfo, String userId);

    MusicPlayTransaction getMusicPlayTransaction(String musicId);

}