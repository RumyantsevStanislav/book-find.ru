insert into roles (privilege)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into users (phone, email, password, first_name, last_name, enabled, account_non_expired, credentials_non_expired,
                   account_non_locked)
values ('11111111', null, '$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K', null, null, true, true, true,
        true),
       ('22222222', null, '$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K', null, null, true, true, true,
        true),
       ('33333333', 'user@gmail.com', '$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K', null, null, true,
        true, true, true);

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 1);

insert into authors(labirint_id, name, role)
values (1, 'Author1', 'Автор'),
       (2, 'Translator1', 'Переводчик'),
       (3, 'Author2', 'Автор'),
       (4, 'Translator2', 'Переводчик');

insert into categories(labirint_id, title)
values (1, 'CategoryTitle1.1'),
       (2, 'CategoryTitle1.2'),
       (3, 'CategoryTitle2');

insert into covers(extension, path)
values ('jpg', '1'),
       ('jpg', '2');

insert into genres(path)
values ('Path1'),
       ('Path2');

insert into publishers(labirint_id, title, description)
values (1, 'PublisherTitle1', 'PublisherDescription1'),
       (2, 'PublisherTitle2', 'PublisherDescription2');

insert into series(labirint_id, title, description)
values (1, 'SeriesTitle1', 'SeriesDescription1'),
       (2, 'SeriesTitle2', 'SeriesDescription2');

insert into books (labirint_id, title, description, price, pages, year, estimation, isbn, status, publisher_id,
                   genre_id, series_id, cover_id)
values (1, 'BookTitle1', 'BookDescription1', 0, 101, 2021, 8.6, 9785907143784, 'ACTIVE', 1, 1, 1, 1),
       (2, 'BookTitle2', 'BookDescription2', 0, 102, 2021, 8.6, 9785907338029, 'ACTIVE', 1, 2, 2, 2);

insert into books_authors
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4);

insert into books_categories
values (1, 1),
       (1, 2),
       (2, 3);