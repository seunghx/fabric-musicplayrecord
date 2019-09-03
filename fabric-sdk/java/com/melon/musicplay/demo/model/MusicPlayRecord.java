package com.melon.musicplay.demo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MusicPlayRecord implements Comparable<MusicPlayRecord>{

    private String key;
    private MusicInfo musicInfo;
    private String docType = "music";
    private String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime listenDateTime;

    public MusicPlayRecord(MusicInfo musicInfo, String userId, String key) {
        this.musicInfo = musicInfo;
        this.userId = userId;
        this.key = key;
        this.listenDateTime = LocalDateTime.now();
    }

    @Override
    public int compareTo(MusicPlayRecord musicPlayRecord) {
        return (-1) * this.listenDateTime.compareTo(musicPlayRecord.listenDateTime);
    }
}
