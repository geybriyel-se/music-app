CREATE TABLE IF NOT EXISTS `music-app`.song_folder_mapping
(
    song_id BIGINT NOT NULL,
    folder_id BIGINT NOT NULL,
    PRIMARY KEY (song_id, folder_id)
);

CREATE INDEX folder_id
    ON `music-app`.song_folder_mapping (folder_id);
