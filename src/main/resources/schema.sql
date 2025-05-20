-- schema.sql: create table for business entity
CREATE TABLE business (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(500),
    category VARCHAR(100),
    phone VARCHAR(20),
    location VARCHAR(255),
    rating DOUBLE,
    like_count INT DEFAULT 0,
    dislike_count INT DEFAULT 0
);
