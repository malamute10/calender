
## 필수 과제 SQL
CREATE TABLE schedule (

    id                  INT UNSIGNED AUTO_INCREMENT             NOT NULL,
    author              VARCHAR(64)                             NOT NULL,
    password            VARCHAR(64)                             NOT NULL,
    content             TEXT                                    NOT NULL,

    created_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL,
    updated_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL ON UPDATE CURRENT_TIMESTAMP(),

    PRIMARY KEY (id)
);


## 도전 과제 SQL
CREATE TABLE user (

    id                  INT UNSIGNED    AUTO_INCREMENT      NOT NULL,
    name                VARCHAR(64)                         NOT NULL,
    email               VARCHAR(64)                         NOT NULL,
    created_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL,
    updated_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL ON UPDATE CURRENT_TIMESTAMP(),

    PRIMARY KEY (id)
);

CREATE TABLE schedule_challenge (

    id                  INT UNSIGNED AUTO_INCREMENT             NOT NULL,
    user_id             INT UNSIGNED                            NOT NULL,
    password            VARCHAR(64)                             NOT NULL,
    content             TEXT                                    NOT NULL,

    created_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL,
    updated_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL ON UPDATE CURRENT_TIMESTAMP(),

    PRIMARY KEY (id),
    CONSTRAINT fk__schedule__user_id FOREIGN KEY (user_id) REFERENCES user(id)
);