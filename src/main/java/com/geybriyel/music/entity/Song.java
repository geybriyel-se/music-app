package com.geybriyel.music.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Song {

    private Long songId;

    private String title;

    private String artist;

    private String album;

}
