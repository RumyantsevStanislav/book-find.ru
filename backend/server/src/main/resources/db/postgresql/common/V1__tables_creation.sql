-- DROP DATABASE if exists "book-find";
-- CREATE DATABASE "book-find";
--CREATE USER test_user WITH password 'qwerty';
--CREATE DATABASE test_database OWNER test_user;
--GRANT ALL privileges ON DATABASE test_db TO test_user;
drop table if exists books cascade;
create table books
(
    id          bigserial,
    title       varchar(255),
    description varchar(5000),
    price       float,
    primary key(id)
);
drop table if exists categories cascade;
create table categories
(
    id    bigserial,
    title varchar(255),
    primary key (id)
);

drop table if exists books_categories cascade;
create table books_categories
(
    book_id  bigint not null,
    category_id bigint not null,
    primary key (book_id, category_id),
    foreign key (book_id) references books (id),
    foreign key (category_id) references categories (id)
);

drop table if exists users;
create table users
(
    id         bigserial,
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
    id   serial,
    name VARCHAR(50) not null,
    primary key (id)
);

drop table if exists users_roles;
create table users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    primary key (user_id, role_id),
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);