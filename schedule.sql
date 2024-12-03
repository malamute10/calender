CREATE TABLE schedule (

    id                  INT UNSIGNED AUTO_INCREMENT             NOT NULL,
    author              VARCHAR(64)                             NOT NULL,
    password            VARCHAR(64)                             NOT NULL,
    content             TEXT                                    NOT NULL,

    created_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL,
    updated_datetime    DATETIME DEFAULT CURRENT_TIMESTAMP()    NOT NULL ON UPDATE CURRENT_TIMESTAMP(),

    PRIMARY KEY (id)
);