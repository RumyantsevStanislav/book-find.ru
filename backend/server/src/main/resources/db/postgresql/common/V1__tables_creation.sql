-- DROP DATABASE if exists "book-find";
-- CREATE DATABASE "book-find";
--CREATE USER test_user WITH password 'qwerty';
--CREATE DATABASE test_database OWNER test_user;
--GRANT ALL privileges ON DATABASE test_db TO test_user;
drop table if exists categories cascade;
create table categories
(
    id          smallserial primary key,
    labirint_id integer,
    title       varchar(255) unique,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists authors cascade;
create table authors
(
    id          smallserial primary key,
    labirint_id integer,
    name        varchar(255) unique,
    role        varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists publishers cascade;
create table publishers
(
    id          smallserial primary key,
    labirint_id integer,
    title       varchar(255) unique,
    description varchar(5000),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists genres cascade;
create table genres
(
    id         smallserial primary key,
    path       varchar(1000) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

drop table if exists series cascade;
create table series
(
    id          smallserial primary key,
    labirint_id integer,
    title       varchar(255) unique,
    description varchar(5000),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists covers cascade;
create table covers
(
    id         smallserial primary key,
    extension  varchar(8),
    path       varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

drop table if exists books cascade;
create table books
(
    id           serial primary key,
    labirint_id  integer,
    title        varchar(255),
    description  varchar(5000),
    price        integer,
    pages        integer,
    estimation   numeric(3, 1),
    publisher_id integer,
    genre_id     integer,
    series_id    integer,
    cover_id     integer,
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp,
    foreign key (publisher_id) references publishers (id),
    foreign key (genre_id) references genres (id),
    foreign key (series_id) references series (id),
    foreign key (cover_id) references covers (id)
);

drop table if exists books_categories cascade;
create table books_categories
(
    book_id     integer not null,
    category_id integer not null,
    primary key (book_id, category_id),
    foreign key (book_id) references books (id),
    foreign key (category_id) references categories (id)
);

drop table if exists books_authors cascade;
create table books_authors
(
    book_id   integer not null,
    author_id integer not null,
    primary key (book_id, author_id),
    foreign key (book_id) references books (id),
    foreign key (author_id) references authors (id)
);

drop table if exists users;
create table users
(
    id         serial,
    phone      VARCHAR(30) not null UNIQUE,
    password   VARCHAR(80) not null,
    email      VARCHAR(50) UNIQUE,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    PRIMARY KEY (id)
);

drop table if exists roles;
create table roles
(
    id   smallserial,
    name VARCHAR(50) not null,
    primary key (id)
);

drop table if exists users_roles;
create table users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);