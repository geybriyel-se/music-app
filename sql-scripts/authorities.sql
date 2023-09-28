CREATE TABLE IF NOT EXISTS `music-app`.authorities
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    authority_name VARCHAR(255) NOT NULL,
    CONSTRAINT authority_name UNIQUE (authority_name)
);
