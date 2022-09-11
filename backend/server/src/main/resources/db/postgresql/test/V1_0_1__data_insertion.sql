insert into roles (privilege)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into users (phone, email, password, first_name, last_name)
values ('11111111', null, '$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K', null, null),
       ('22222222', null, '$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K', null, null),
       ('33333333', 'user@gmail.com', '$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K', null, null);

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 1);