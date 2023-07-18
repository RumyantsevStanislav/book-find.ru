drop table if exists authors cascade;
create table authors
(
    id          int primary key auto_increment,
    labirint_id integer,
    name        varchar(255),
    role        varchar(255),
    unique (name, role),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
