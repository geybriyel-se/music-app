package com.geybriyel.music.service.impl;

import com.geybriyel.music.entity.Song;
import com.geybriyel.music.mapper.SongMetadataMapper;
import com.geybriyel.music.service.SongMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongMetadataServiceImpl implements SongMetadataService {

    @Autowired
    private SongMetadataMapper songMetadataMapper;

    @Override
    public List<Song> selectMetadataList() {
        return songMetadataMapper.selectMetadataList();
    }

    @Override
    public Song selectMetadataBySongId(Long songId) {
        return songMetadataMapper.selectMetadataBySongId(songId);
    }

    @Override
    public int insertSongMetadata(Song song) {
        songMetadataMapper.insertSongMetadata(song);
        return 0;
    }
}
