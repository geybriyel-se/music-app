CREATE TABLE IF NOT EXISTS `music-app`.song_metadata
(
    song_id BIGINT NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    album VARCHAR(255) NOT NULL
);
