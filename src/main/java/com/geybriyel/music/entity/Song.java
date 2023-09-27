package com.geybriyel.music.entity;

import lombok.Data;

@Data
public class Song {

    private Long songId;

    private String title;

    private String artist;

    private String album;

}
