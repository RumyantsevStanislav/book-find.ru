drop table if exists books_categories cascade;
create table books_categories
(
    book_id     integer not null,
    category_id integer not null,
    primary key (book_id, category_id),
    foreign key (book_id) references books (id),
    foreign key (category_id) references categories (id)
);
