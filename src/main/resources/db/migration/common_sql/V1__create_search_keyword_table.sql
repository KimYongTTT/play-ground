CREATE TABLE search_keyword
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    keyword VARCHAR(255)          NOT NULL,
    hit     BIGINT                NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE INDEX ux_searchkeyword_keyword(keyword)
) AUTO_INCREMENT = 1;
