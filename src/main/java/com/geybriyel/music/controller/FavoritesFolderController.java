package com.geybriyel.music.controller;

import com.geybriyel.music.entity.*;
import com.geybriyel.music.enums.ErrorCodes;
import com.geybriyel.music.mapper.UserMapper;
import com.geybriyel.music.response.ApiResponse;
import com.geybriyel.music.service.FavoritesFolderService;
import com.geybriyel.music.service.SongMetadataService;
import com.geybriyel.music.service.SongToFolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/favorites")
public class FavoritesFolderController {

    @Autowired
    private FavoritesFolderService folderService;

    @Autowired
    private SongToFolderService songToFolderService;

    @Autowired
    private SongMetadataService songMetadataService;

    @Autowired
    private UserMapper userService;


    @GetMapping("/id/{folderId}")
    private ApiResponse getFolderById(@PathVariable Long folderId) {
        FavoritesFolder favoritesFolder = folderService.selectFavoritesFolderById(folderId);
        if (favoritesFolder == null) {
            log.info("Invalid folder id {}", folderId);
            return new ApiResponse(ErrorCodes.INVALID_FOLDER_ID.getCode(), ErrorCodes.INVALID_FOLDER_ID.getMessage());
        }
        log.info("Retrieved folder with id {}: {}", folderId, favoritesFolder);
        return new ApiResponse(HttpStatus.OK.value(), favoritesFolder);
    }

    @GetMapping("/username/{username}")
    private ApiResponse getFolderNamesByUsername(@PathVariable String username) {
        User user = userService.selectUserByUserName(username);
        boolean isValid = validateUsername(user);

        if (!isValid) {
            log.info("Failed attempt to get folder. The username is invalid");
            return new ApiResponse(ErrorCodes.USER_DOES_NOT_EXIST.getCode(), ErrorCodes.USER_DOES_NOT_EXIST.getMessage());
        }

        List<FavoritesFolder> favoritesFolders = folderService.selectFavoritesFolderListByUsername(username);
        if (favoritesFolders.size() == 0) {
            log.info("User has no folders");
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "You don't have any folders");
        }
        log.info("Retrieved folder/s of user {}: {}", username, favoritesFolders);
        return new ApiResponse(HttpStatus.OK.value(), favoritesFolders);
    }

    private boolean validateUsername(User user) {
        if (user == null) {
            return false;
        }
        return true;
    }

    @GetMapping("/name")
    private ApiResponse getFolderByFolderName(@RequestBody FavoritesFolder folder) {
        String username = folder.getUsername();
        User user = userService.selectUserByUserName(username);
        boolean isValid = validateUsername(user);

        if (!isValid) {
            log.info("Failed attempt to get folder. The username is invalid");
            return new ApiResponse(ErrorCodes.USER_DOES_NOT_EXIST.getCode(), ErrorCodes.USER_DOES_NOT_EXIST.getMessage());
        }

        String folderName = folder.getFolderName();
        List<FavoritesFolder> userFolders = folderService.selectFavoritesFolderListByUsername(username);
        // user has no folders
        if (userFolders.isEmpty()) {
            log.info("User has no folders");
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "You don't have any folders");
        }

        // user has folders. check if the name exists
        boolean folderExists = folderExists(userFolders, folderName);
        if (!folderExists) {
            log.info("{} has no folder '{}'", username, folderName);
            return new ApiResponse(ErrorCodes.FOLDER_DOES_NOT_EXIST.getCode(), ErrorCodes.FOLDER_DOES_NOT_EXIST.getMessage());
        } else {
            for (FavoritesFolder userFolder : userFolders) {
                if (userFolder.getFolderName().equals(folderName)) {
                    log.info("Folder retrieved: {}", userFolder);
                    return new ApiResponse(HttpStatus.OK.value(), userFolder);
                }
            }
        }
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
    }

    @PostMapping("/new")
    public ApiResponse createNewFolder(@RequestBody FavoritesFolder favoritesFolder) {
        String username = favoritesFolder.getUsername();
        User user = userService.selectUserByUserName(username);
        boolean isValid = validateUsername(user);
        if (!isValid) {
            log.info("Failed attempt to create folder. The username is invalid");
            return new ApiResponse(ErrorCodes.USER_DOES_NOT_EXIST.getCode(), ErrorCodes.USER_DOES_NOT_EXIST.getMessage());
        }

        String folderName = favoritesFolder.getFolderName();
        List<FavoritesFolder> favoritesFolders = folderService.selectFavoritesFolderListByUsername(username);

        if (favoritesFolders != null) {
            if (folderExists(favoritesFolders, folderName)) {
                log.error("Failed attempt to create folder. User has an existing folder with the same name as '{}'", folderName);
                return new ApiResponse(ErrorCodes.FOLDER_ALREADY_EXISTS.getCode(), ErrorCodes.FOLDER_ALREADY_EXISTS.getMessage());
            }
        }

        int i = folderService.insertFavoritesFolder(favoritesFolder);
        log.info("Successfully created new folder");
        return new ApiResponse(HttpStatus.OK.value(), "New folder created");
    }

    @DeleteMapping("/del/{id}")
    public ApiResponse deleteFolder(@PathVariable Long id) {
        int i = folderService.deleteFavoritesFolderById(id);
        // delete all songs in folder
        int i1 = songToFolderService.deleteSongFolderMapperByFolderId(id);
        log.info("Successfully deleted folder");
        return new ApiResponse(HttpStatus.OK.value(), "Folder deleted");
    }

    public boolean folderExists(List<FavoritesFolder> favoritesFolders, String folderName) {
        for (FavoritesFolder folder : favoritesFolders) {
            if (folder.getFolderName().equals(folderName)) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/add")
    public ApiResponse addSongToFolder(@RequestBody SongFolderMapper songFolderMapper) {
        Long folderId = songFolderMapper.getFolderId();
        Long songId = songFolderMapper.getSongId();

        FavoritesFolder favoritesFolder = folderService.selectFavoritesFolderById(folderId);
        if (favoritesFolder == null) {
            log.info("Failed attempt to add song. The folder id is invalid");
            return new ApiResponse(ErrorCodes.INVALID_FOLDER_ID.getCode(), ErrorCodes.INVALID_FOLDER_ID.getMessage());
        }

        Song song = songMetadataService.selectMetadataBySongId(songId);
        if (song == null) {
            log.info("Failed attempt to add song. The song id is invalid");
            return new ApiResponse(ErrorCodes.INVALID_SONG_ID.getCode(), ErrorCodes.INVALID_SONG_ID.getMessage());
        }

        SongFolderMapper result = songToFolderService.selectSongFolderMapper(songFolderMapper);
        if (result != null) {
            log.info("Failed attempt to add song to folder. Song already exists in the folder");
            return new ApiResponse(ErrorCodes.SONG_ALREADY_EXISTS.getCode(), ErrorCodes.SONG_ALREADY_EXISTS.getMessage());
        }

        int i = songToFolderService.insertSongToFolder(songFolderMapper);
        if (i != 0) {
            log.info("Failed to add song to folder");
            return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to add song");
        }

        String res = "Song " + songId + " added to folder " + folderId;
        log.info("Success. " + res);
        return new ApiResponse(HttpStatus.OK.value(), res);
    }

    @GetMapping("/all/{username}")
    public ApiResponse getAllFoldersAndSongsByUsername(@PathVariable String username) {
        User user = userService.selectUserByUserName(username);
        boolean isValid = validateUsername(user);
        if (!isValid) {
            log.info("Failed attempt to get folder and contents. The username is invalid");
            return new ApiResponse(ErrorCodes.USER_DOES_NOT_EXIST.getCode(), ErrorCodes.USER_DOES_NOT_EXIST.getMessage());
        }

        FavoritesContentRes res = new FavoritesContentRes();
        List<FavoritesFolder> userFolders = folderService.selectFavoritesFolderListByUsername(username);

        if (userFolders.isEmpty()) {
            log.info("User has no folders");
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "You don't have any folders");
        }

        for (FavoritesFolder folder : userFolders) {
            Long folderId = folder.getFolderId();
            List<SongFolderMapper> songs = songToFolderService.selectSongsByFolderId(folderId);
            if (songs.isEmpty()) {
                log.info("There are no songs in the folder");
                res.addSongToFolder(folder, null);
            }
            List<Long> songIds = songs.stream().map(song -> song.getSongId()).collect(Collectors.toList());
            List<Song> allSongs = songIds.stream().map(id -> songMetadataService.selectMetadataBySongId(id)).collect(Collectors.toList());

            allSongs.forEach(song -> {
                res.addSongToFolder(folder, song);
                log.info("Added song {} to folder {}", song.getSongId(), folder.getFolderId());
            });
        }

        log.info("Folders and contents retrieved successfully: {}", res.getFolderSongMap());
        return new ApiResponse(HttpStatus.OK.value(), res);
    }


    @GetMapping("/songs/{folderId}")
    public ApiResponse getAllSongsByFolderId(@PathVariable Long folderId) {
        FavoritesFolder folder = folderService.selectFavoritesFolderById(folderId);
        if (folder == null) {
            log.info("Invalid folder id / folder does not exist");
            return new ApiResponse(ErrorCodes.INVALID_FOLDER_ID.getCode(), ErrorCodes.INVALID_FOLDER_ID.getMessage());
        }

        List<SongFolderMapper> mapperList = songToFolderService.selectSongsByFolderId(folderId);
        if (mapperList.isEmpty()) {
            log.info("Folder has no songs");
            return new ApiResponse(HttpStatus.NO_CONTENT.value(), "This folder is empty");
        }

        List<Long> songIds = mapperList.stream()
                .map(mapper -> mapper.getSongId())
                .collect(Collectors.toList());
        List<Song> allSongs = songIds.stream()
                .map(id -> songMetadataService.selectMetadataBySongId(id))
                .collect(Collectors.toList());

        log.info("Retrieved all songs in the folder: {}", allSongs);
        return new ApiResponse(HttpStatus.OK.value(), allSongs);
    }
}
