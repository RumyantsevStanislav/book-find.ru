-- DROP DATABASE if exists "book-find";
-- CREATE DATABASE "book-find";
-- CREATE USER test_user WITH password $ { .env.password};
-- CREATE DATABASE test_database OWNER $ {.flyway.owner};
--GRANT ALL privileges ON DATABASE test_db TO test_user;
drop table if exists categories cascade;
create table categories
(
    id          serial primary key,
    labirint_id integer,
    title       varchar(255) unique,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists authors cascade;
create table authors
(
    id          serial primary key,
    labirint_id integer,
    name        varchar(255),
    role        varchar(255),
    unique (name, role),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists publishers cascade;
create table publishers
(
    id          serial primary key,
    labirint_id integer,
    title       varchar(255) unique,
    description varchar(5000),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists genres cascade;
create table genres
(
    id         serial primary key,
    path       varchar(1000) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

drop table if exists series cascade;
create table series
(
    id          serial primary key,
    labirint_id integer,
    title       varchar(255) unique,
    description varchar(5000),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists covers cascade;
create table covers
(
    id         serial primary key,
    extension  varchar(8),
    path       varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

drop table if exists books cascade;
create table books
(
    id                serial primary key,
    labirint_id       integer,
    title             varchar(255),
    description       varchar(5000),
    price             integer CHECK (price >= 0),
    pages             integer,
    year              integer,
    estimation        numeric(3, 1),
    estimations_count integer,
    isbn              varchar(64),
    status            varchar(255),
    publisher_id      integer,
    genre_id          integer,
    series_id         integer,
    cover_id          integer,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp,
    unique (isbn),
    unique (title, publisher_id, series_id),
    foreign key (publisher_id) references publishers (id),
    foreign key (genre_id) references genres (id),
    foreign key (series_id) references series (id),
    foreign key (cover_id) references covers (id)
);

drop table if exists isbns cascade;
create table isbns
(
    id         serial primary key,
    isbn       varchar(64),
    book_id    integer not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    foreign key (book_id) references books (id)
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
    id                      serial,
    phone                   VARCHAR(30) unique,
    email                   VARCHAR(50) unique,
    password                VARCHAR(80) not null,
    first_name              VARCHAR(50),
    last_name               VARCHAR(50),
    enabled                 boolean,
    account_non_expired     boolean,
    credentials_non_expired boolean,
    account_non_locked      boolean,
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp,
    last_login_at           timestamp default current_timestamp,
    constraint check_not_null check (phone is not null or email is not null),
    PRIMARY KEY (id)
);
create type privilege as enum ('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN');
drop table if exists roles;
create table roles
(
    id         serial,
    privilege  varchar(50) unique not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (id)
);

insert into roles (privilege)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

drop table if exists users_roles;
create table users_roles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    primary key (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

drop table if exists personal_books cascade;
create table personal_books
(
    id         serial primary key,
    isbn       varchar(64)                         not null,
    phone      varchar(255),
    email      varchar(255),
    status     varchar(255)                        not null,
    estimation integer,
    comment    varchar(255),
    created_at timestamp default current_timestamp not null,
    updated_at timestamp default current_timestamp not null,
    unique (isbn, phone, email), -- for postgres 15+ instead of UNIQUE INDEXES /*UNIQUE NULLS NOT DISTINCT*/
    foreign key (isbn) references books (isbn),
    foreign key (phone) references users (phone),
    foreign key (email) references users (email)
);
CREATE UNIQUE INDEX pb_unique_all ON personal_books (isbn, phone, email)
    WHERE phone IS NOT NULL AND email IS NOT NULL;
CREATE UNIQUE INDEX pb_unique_email ON personal_books (isbn, email)
    WHERE phone IS NULL;
CREATE UNIQUE INDEX pb_unique_phone ON personal_books (isbn, phone)
    WHERE email IS NULL;

drop table if exists verification_tokens cascade;
create table verification_tokens
(
    id          serial primary key,
    user_id     bigint                              not null,
    token       varchar(255)                        not null,
    expiry_date timestamp                           not null,
    created_at  timestamp default current_timestamp not null,
    updated_at  timestamp default current_timestamp not null,
    foreign key (user_id) references users (id)
);

drop table if exists password_reset_tokens cascade;
create table password_reset_tokens
(
    id          serial primary key,
    user_id     bigint                              not null,
    token       varchar(255)                        not null,
    expiry_date timestamp                           not null,
    created_at  timestamp default current_timestamp not null,
    updated_at  timestamp default current_timestamp not null,
    foreign key (user_id) references users (id)
);

drop table if exists reviews cascade;
create table reviews
(
    id         serial primary key,
    review     varchar(5000),
    estimation smallint,
    book_id    integer not null,
    user_id    integer not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    unique (book_id, user_id),
    foreign key (book_id) references books (id),
    foreign key (user_id) references users (id)
);
