package com.geybriyel.music.service;

import com.geybriyel.music.entity.SongFolderMapper;

import java.util.List;

public interface SongToFolderService {

    List<SongFolderMapper> selectSongsByFolderId(Long folderId);

    SongFolderMapper selectSongFolderMapper(SongFolderMapper songFolderMapper);

    /**
     *
     * @param songFolderMapper
     * @return 0:successful
     */
    int insertSongToFolder(SongFolderMapper songFolderMapper);

    /**
     *
     * @param songFolderMapper
     * @return 0:successful
     */
    int deleteSongFromFolder(SongFolderMapper songFolderMapper);

    /**
     *
     * @param folderId
     * @return 0:successful
     */
    int deleteSongFolderMapperByFolderId(Long folderId);

}
