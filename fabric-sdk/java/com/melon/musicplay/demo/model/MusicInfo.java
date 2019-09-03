package com.melon.musicplay.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MusicInfo {
    private String musicId;
    private String title;
    // private Album album
    private String album;
    private List<String> singers = new ArrayList<>();
}