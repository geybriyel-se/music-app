CREATE TABLE IF NOT EXISTS `music-app`.users_authorities
(
    user_id BIGINT NOT NULL,
    authority_id BIGINT NOT NULL COMMENT '1:ADMIN 2:USER',
    PRIMARY KEY (user_id, authority_id),
    CONSTRAINT users_authorities_ibfk_1
        FOREIGN KEY (user_id) REFERENCES `music-app`.users (id),
    CONSTRAINT users_authorities_ibfk_2
        FOREIGN KEY (authority_id) REFERENCES `music-app`.authorities (id)
);

CREATE INDEX authority_id
    ON `music-app`.users_authorities (authority_id);
