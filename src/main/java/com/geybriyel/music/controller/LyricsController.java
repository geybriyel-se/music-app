package com.geybriyel.music.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geybriyel.music.enums.ErrorCodes;
import com.geybriyel.music.service.RedisService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/lyrics")
public class LyricsController {

    @Autowired
    private RedisService redisService;

    @Value("${api.rapidapi.hostkey}")
    private String apiKey;


    /**
     * Endpoint to get song info based on keyword.
     * This is where the song ID can be retrieved
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<String> searchSongByKeyword(@PathVariable String keyword) {
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.get("https://genius-song-lyrics1.p.rapidapi.com/search/?q=%3C" + keyword + "%3E")
                    .header("X-RapidAPI-Host", "genius-song-lyrics1.p.rapidapi.com")
                    .header("X-RapidAPI-Key", apiKey)
                    .asString();
            String body = response.getBody();
            log.info("Songs with keyword '{}' retrieved: {}", keyword, body);
            return ResponseEntity.ok(body);
        } catch (UnirestException e) {
            e.printStackTrace();
            String errorMessage = "An error occurred while retrieving the data";
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }


    /**
     * Endpoint to get the lyrics of the song
     * @param id
     * @return
     */
    @GetMapping("/getLyrics/{id}")
    public ResponseEntity getLyricsByID(@PathVariable String id) {
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.get("https://genius-song-lyrics1.p.rapidapi.com/song/lyrics/?id=" + id)
                    .header("X-RapidAPI-Host", "genius-song-lyrics1.p.rapidapi.com")
                    .header("X-RapidAPI-Key", apiKey)
                    .asString();
            String body = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(body);
                redisService.setValue("lyrics:" + id, jsonNode);
                log.info("Lyrics of song with id {} retrieved: {}", id, body);
                return ResponseEntity.ok(body);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorCodes.JSON_PARSE_ERROR);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorCodes.INTERNAL_SERVER_ERROR);
        }
    }

}
