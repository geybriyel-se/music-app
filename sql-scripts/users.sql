CREATE TABLE IF NOT EXISTS `music-app`.users
(
    id                      BIGINT AUTO_INCREMENT,
    username                VARCHAR(64) COLLATE utf8mb4_bin      NOT NULL,
    password                VARCHAR(255)                         NOT NULL,
    email                   VARCHAR(255)                         NULL,
    profile_pic_url         VARCHAR(255)                         NULL,
    registration_date       TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_login              TIMESTAMP                            NULL,
    account_non_expired     TINYINT(1) DEFAULT 1                 NOT NULL COMMENT '1: not -expired, 0:expired',
    account_non_locked      TINYINT(1) DEFAULT 1                 NOT NULL COMMENT '1: not - locked, 0:locked',
    credentials_non_expired TINYINT(1) DEFAULT 1                 NOT NULL COMMENT '1: not -expired, 0:expired',
    enabled                 TINYINT(1) DEFAULT 1                 NOT NULL COMMENT '1: enabled, 0:disabled',
    CONSTRAINT users_id_uindex UNIQUE (id),
    CONSTRAINT users_id_uindex_2 UNIQUE (id),
    CONSTRAINT users_username_uindex UNIQUE (username),
    CONSTRAINT users_username_uindex_2 UNIQUE (username)
);

ALTER TABLE `music-app`.users
    ADD PRIMARY KEY (id);

CREATE DEFINER = `music-admin`@localhost TRIGGER `music-app`.after_insert_user
    AFTER INSERT
    ON `music-app`.users
    FOR EACH ROW
BEGIN
    INSERT INTO `music-app`.users_authorities (user_id, authority_id)
    VALUES (NEW.id, 2);
END;