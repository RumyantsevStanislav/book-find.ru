drop table if exists publishers cascade;
create table publishers
(
    id          int primary key auto_increment,
    labirint_id integer,
    title       varchar(255) unique,
    description varchar(5000),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
