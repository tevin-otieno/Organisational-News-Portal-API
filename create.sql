CREATE DATABASE organisational_news_portal;
\c organisational_news_portal

CREATE TABLE departments (
id SERIAL PRIMARY KEY,
name VARCHAR,
description VARCHAR,
size INT
);

CREATE TABLE news (
id SERIAL PRIMARY KEY,
title VARCHAR,
news_type VARCHAR,
department_id INT,
user_id INT,
description VARCHAR
);

CREATE TABLE department_news (
id SERIAL PRIMARY KEY,
department_id INT,
news_id INT
);

CREATE TABLE staff (
id SERIAL PRIMARY KEY,
name VARCHAR,
position VARCHAR,
staff_role VARCHAR
);

CREATE TABLE users_departments (
id SERIAL PRIMARY KEY,
user_id INT,
department_id INT
);

CREATE DATABASE organisational_news_portal_test WITH TEMPLATE organisational_news_portal;