drop table if exists genres cascade;
create table genres
(
    id         int primary key auto_increment,
    path       varchar(1000) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
