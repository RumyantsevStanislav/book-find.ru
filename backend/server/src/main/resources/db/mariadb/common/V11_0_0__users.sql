drop table if exists users;
create table users
(
    id                      int primary key auto_increment,
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
    constraint check_not_null check (phone is not null or email is not null)
);
