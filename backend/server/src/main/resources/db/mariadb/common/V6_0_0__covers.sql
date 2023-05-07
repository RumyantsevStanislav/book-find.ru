drop table if exists covers cascade;
create table covers
(
    id         int primary key auto_increment,
    extension  varchar(8),
    path       varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
