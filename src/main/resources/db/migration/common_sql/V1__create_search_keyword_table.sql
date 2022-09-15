CREATE TABLE search_keyword
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    keyword VARCHAR(255)          NOT NULL,
    hit     BIGINT                NOT NULL DEFAULT 1,
    last_search_datetime            datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE INDEX ux_searchkeyword_01(keyword)
)AUTO_INCREMENT = 1;
