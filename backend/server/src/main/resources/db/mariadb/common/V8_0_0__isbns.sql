drop table if exists isbns cascade;
create table isbns
(
    id         int primary key auto_increment,
    isbn       bigint,
    book_id    integer not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    foreign key (book_id) references books (id)
);
