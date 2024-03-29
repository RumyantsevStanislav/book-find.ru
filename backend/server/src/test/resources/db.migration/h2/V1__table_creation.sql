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
-- insert into books
-- (title, description, price)
-- values ('Book1', 'BookDesc1', 100),
--        ('Book2', 'BookDesc2', 200),
--        ('Book3', 'BookDesc3', 300),
--        ('Book4', 'BookDesc4', 400);

drop table if exists categories cascade;
create table categories
(
    id    bigserial,
    title varchar(255),
    primary key (id)
);
-- insert into categories
--     (title)
-- values ('Food'),
--        ('Devices');

drop table if exists books_categories cascade;
create table books_categories
(
    book_id  bigint not null,
    category_id bigint not null,
    primary key (book_id, category_id),
    foreign key (book_id) references books (id),
    foreign key (category_id) references categories (id)
);
-- insert into books_categories (book_id, category_id)
-- values (1, 1),
--        (2, 1),
--        (3, 1),
--        (4, 2);

-- drop table if exists users;
-- create table users
-- (
--     id         bigserial,
--     phone      VARCHAR(30) not null UNIQUE,
--     password   VARCHAR(80) not null,
--     email      VARCHAR(50) UNIQUE,
--     first_name VARCHAR(50),
--     last_name  VARCHAR(50),
--     PRIMARY KEY (id)
-- );
--
-- drop table if exists roles;
-- create table roles
-- (
--     id   serial,
--     name VARCHAR(50) not null,
--     primary key (id)
-- );
--
-- drop table if exists users_roles;
-- create table users_roles
-- (
--     user_id INT NOT NULL,
--     role_id INT NOT NULL,
--     primary key (user_id, role_id),
--     FOREIGN KEY (user_id)
--         REFERENCES users (id),
--     FOREIGN KEY (role_id)
--         REFERENCES roles (id)
-- );
--
-- insert into roles (name)
-- values ('ROLE_CUSTOMER'),
--        ('ROLE_MANAGER'),
--        ('ROLE_ADMIN');
--
-- insert into users (phone, password, first_name, last_name, email)
-- values ('11111111', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin', 'admin',
--         'admin@gmail.com');
--
-- insert into users_roles (user_id, role_id)
-- values (1, 1),
--        (1, 2),
--        (1, 3);