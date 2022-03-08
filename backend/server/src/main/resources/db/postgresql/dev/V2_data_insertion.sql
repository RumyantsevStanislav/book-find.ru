insert into books
(title, description, price)
values ('Book1', 'BookDesc1', 100),
       ('Book2', 'BookDesc2', 200),
       ('Book3', 'BookDesc3', 300),
       ('Book4', 'BookDesc4', 400);

insert into categories
    (title)
values ('Food'),
       ('Devices');

insert into books_categories (book_id, category_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 2);

-- insert into roles (name)
-- values ('ROLE_CUSTOMER'),
--        ('ROLE_MANAGER'),
--        ('ROLE_ADMIN');
--
-- insert into users (phone, password, first_name, last_name, email)
-- values ('11111111', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin', 'admin',
--         'admin@gmail.com');
--
-- insert into users_roles (user_id, role_id)
-- values (1, 1),
--        (1, 2),
--        (1, 3);