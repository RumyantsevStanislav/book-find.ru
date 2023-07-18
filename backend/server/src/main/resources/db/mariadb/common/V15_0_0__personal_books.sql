drop table if exists personal_books cascade;
create table personal_books
(
    id         int primary key auto_increment,
    isbn       varchar(64)                         not null,
    phone      varchar(255),
    email      varchar(255),
    status     varchar(255)                        not null,
    estimation integer,
    comment    varchar(255),
    created_at timestamp default current_timestamp not null,
    updated_at timestamp default current_timestamp not null,
    unique (isbn, phone, email),
    foreign key (isbn) references books (isbn),
    foreign key (phone) references users (phone),
    foreign key (email) references users (email),
    constraint check_not_null check (phone is not null or email is not null)
);
CREATE UNIQUE INDEX pb_unique_all ON personal_books (isbn, phone, email)


