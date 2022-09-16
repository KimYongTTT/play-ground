CREATE TABLE search_keyword
(
    keyword VARCHAR(255)          NOT NULL,
    hit     BIGINT                NOT NULL DEFAULT 1,
    last_search_datetime          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (keyword)
)AUTO_INCREMENT = 1;
