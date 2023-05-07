drop table if exists reviews cascade;
create table reviews
(
    id         int primary key auto_increment,
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
