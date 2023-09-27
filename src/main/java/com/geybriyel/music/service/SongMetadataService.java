package com.geybriyel.music.service;

import com.geybriyel.music.entity.Song;

import java.util.List;

public interface SongMetadataService {

    List<Song> selectMetadataList();

    Song selectMetadataBySongId(Long songId);

    /**
     *
     * @param song
     * @return 0: successful
     */
    int insertSongMetadata(Song song);

}
