package com.geybriyel.music.mapper;

import com.geybriyel.music.entity.FavoritesFolder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoritesFolderMapper {

    List<FavoritesFolder> selectFavoritesFolderList();

    List<FavoritesFolder> selectFavoritesFolderListByUsername(String username);

    FavoritesFolder selectFavoritesFolderById(Long id);

    int insertFavoritesFolder(FavoritesFolder favoritesFolder);

    int deleteFavoritesFolderById(Long id);


}
