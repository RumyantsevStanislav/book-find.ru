drop table if exists books_authors cascade;
create table books_authors
(
    book_id   integer not null,
    author_id integer not null,
    primary key (book_id, author_id),
    foreign key (book_id) references books (id),
    foreign key (author_id) references authors (id)
);
