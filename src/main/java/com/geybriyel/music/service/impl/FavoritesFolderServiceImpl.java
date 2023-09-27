package com.geybriyel.music.service.impl;

import com.geybriyel.music.entity.FavoritesFolder;
import com.geybriyel.music.mapper.FavoritesFolderMapper;
import com.geybriyel.music.service.FavoritesFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesFolderServiceImpl implements FavoritesFolderService {

    @Autowired
    private FavoritesFolderMapper favoritesFolderMapper;

    @Override
    public List<FavoritesFolder> selectFavoritesFolderList() {
        return favoritesFolderMapper.selectFavoritesFolderList();
    }

    @Override
    public FavoritesFolder selectFavoritesFolderById(Long id) {
        return favoritesFolderMapper.selectFavoritesFolderById(id);
    }

    @Override
    public List<FavoritesFolder> selectFavoritesFolderListByUsername(String username) {
        return favoritesFolderMapper.selectFavoritesFolderListByUsername(username);
    }

    @Override
    public int insertFavoritesFolder(FavoritesFolder favoritesFolder) {
        favoritesFolderMapper.insertFavoritesFolder(favoritesFolder);
        return 0;
    }

    @Override
    public int deleteFavoritesFolderById(Long id) {
        favoritesFolderMapper.deleteFavoritesFolderById(id);
        return 0;
    }
}
