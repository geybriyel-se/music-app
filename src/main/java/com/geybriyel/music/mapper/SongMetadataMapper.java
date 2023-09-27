package com.geybriyel.music.mapper;

import com.geybriyel.music.entity.Song;
import com.geybriyel.music.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongMetadataMapper {

    List<Song> selectMetadataList();

    Song selectMetadataBySongId(Long songId);

    int insertSongMetadata(Song song);
}
