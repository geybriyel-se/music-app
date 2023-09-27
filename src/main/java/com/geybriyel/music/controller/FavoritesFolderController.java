package com.geybriyel.music.controller;

import com.geybriyel.music.entity.FavoritesFolder;
import com.geybriyel.music.enums.ErrorCodes;
import com.geybriyel.music.response.ApiResponse;
import com.geybriyel.music.service.FavoritesFolderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/favorites")
public class FavoritesFolderController {

    @Autowired
    private FavoritesFolderService folderService;

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
    private ApiResponse getFolderByUsername(@PathVariable String username) {
        // TODO: check if username is valid


        List<FavoritesFolder> favoritesFolders = folderService.selectFavoritesFolderListByUsername(username);
        if (favoritesFolders.size() == 0) {
            log.info("User has no folders");
            return new ApiResponse(HttpStatus.OK.value(), "You don't have any folders");
        }
        log.info("Retrieved folder/s of user {}: {}", username, favoritesFolders);
        return new ApiResponse(HttpStatus.OK.value(), favoritesFolders);
    }

    @GetMapping("/name")
    private ApiResponse getFolderByFolderName(@RequestBody FavoritesFolder folder) {
        // TODO: Check is username is valid
        String folderName = folder.getFolderName();
        String username = folder.getUsername();
        List<FavoritesFolder> userFolders = folderService.selectFavoritesFolderListByUsername(username);
        // user has no folders
        if (userFolders.isEmpty()) {
            log.info("User has no folders");
            return new ApiResponse(HttpStatus.OK.value(), "You don't have any folders");
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

}
