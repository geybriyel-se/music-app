package com.geybriyel.music.service.impl;

import com.geybriyel.music.entity.SongFolderMapper;
import com.geybriyel.music.mapper.SongToFolderMapper;
import com.geybriyel.music.service.SongToFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongToFolderServiceImpl implements SongToFolderService {

    @Autowired
    private SongToFolderMapper songToFolderMapper;


    @Override
    public List<SongFolderMapper> selectSongsByFolderId(Long folderId) {
        return songToFolderMapper.selectSongFolderMapperByFolderId(folderId);
    }

    @Override
    public SongFolderMapper selectSongFolderMapper(SongFolderMapper songFolderMapper) {
        return songToFolderMapper.selectSongFolderMapper(songFolderMapper);
    }

    @Override
    public int insertSongToFolder(SongFolderMapper songFolderMapper) {
        songToFolderMapper.insertSongFolderMapper(songFolderMapper);
        return 0;
    }

    @Override
    public int deleteSongFromFolder(SongFolderMapper songFolderMapper) {
        songToFolderMapper.deleteSongFolderMapper(songFolderMapper);
        return 0;
    }

    @Override
    public int deleteSongFolderMapperByFolderId(Long folderId) {
        songToFolderMapper.deleteSongFolderMapperByFolderId(folderId);
        return 0;
    }
}
