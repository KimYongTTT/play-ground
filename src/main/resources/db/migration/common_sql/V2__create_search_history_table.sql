CREATE TABLE search_history
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    search_keyword_id       BIGINT                NOT NULL,
    search_datetime         datetime              NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)AUTO_INCREMENT = 1;
