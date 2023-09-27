package com.geybriyel.music.mapper;

import com.geybriyel.music.entity.SongFolderMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongToFolderMapper {

    List<SongFolderMapper> selectSongFolderMapperByFolderId(Long folderId);

    SongFolderMapper selectSongFolderMapper(SongFolderMapper songFolderMapper);

    int insertSongFolderMapper(SongFolderMapper songFolderMapper);

    int deleteSongFolderMapper(SongFolderMapper songFolderMapper);

    int deleteSongFolderMapperByFolderId(Long folderId);

}
