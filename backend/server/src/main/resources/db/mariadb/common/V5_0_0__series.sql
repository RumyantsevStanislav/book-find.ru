drop table if exists series cascade;
create table series
(
    id          int primary key auto_increment,
    labirint_id integer,
    title       varchar(255) unique,
    description varchar(5000),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
