CREATE TABLE IF NOT EXISTS `music-app`.folder_favorites
(
    folder_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    folder_name VARCHAR(255) NOT NULL
);

CREATE INDEX username
    ON `music-app`.folder_favorites (username);
