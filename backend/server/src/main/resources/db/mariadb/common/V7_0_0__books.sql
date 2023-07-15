drop table if exists books cascade;
create table books
(
#     id                bigint unsigned zerofill primary key auto_increment,
    id                int primary key auto_increment,
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
