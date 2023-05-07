drop table if exists categories cascade;
create table categories
(
    id          int primary key auto_increment,
    labirint_id integer,
    title       varchar(255) unique,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
