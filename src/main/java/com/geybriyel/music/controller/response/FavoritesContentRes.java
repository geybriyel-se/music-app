package com.geybriyel.music.controller.response;

import com.geybriyel.music.entity.FavoritesFolder;
import com.geybriyel.music.entity.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesContentRes {

    private Map<FavoritesFolder, List<Song>> folderSongMap;

    public FavoritesContentRes() {
        folderSongMap = new HashMap<>();
    }

    public void addSongToFolder(FavoritesFolder folder, Song song) {
        folderSongMap.computeIfAbsent(folder, k -> new ArrayList<>()).add(song);
    }

    public List<Song> getSongsInFolder(FavoritesFolder folder) {
        return folderSongMap.getOrDefault(folder, new ArrayList<>());
    }

    public Map<FavoritesFolder, List<Song>> getFolderSongMap() {
        return folderSongMap;
    }

}
