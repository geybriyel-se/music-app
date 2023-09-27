package com.geybriyel.music.service;

import com.geybriyel.music.entity.FavoritesFolder;

import java.util.List;

public interface FavoritesFolderService {

    List<FavoritesFolder> selectFavoritesFolderList();

    FavoritesFolder selectFavoritesFolderById(Long id);

    List<FavoritesFolder> selectFavoritesFolderListByUsername(String username);

    /**
     *
     * @param favoritesFolder
     * @return 0: successful
     */
    int insertFavoritesFolder(FavoritesFolder favoritesFolder);

    /**
     *
     * @param id
     * @return 0:successful
     */
    int deleteFavoritesFolderById(Long id);

}
