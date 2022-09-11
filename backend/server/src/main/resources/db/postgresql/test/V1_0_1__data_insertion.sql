-- insert into books
--     (title, description, price)
-- values ('Book1', 'BookDesc1', 100),
--        ('Book2', 'BookDesc2', 200),
--        ('Book3', 'BookDesc3', 300),
--        ('Book4', 'BookDesc4', 400);
--
-- insert into categories
--     (title)
-- values ('Food'),
--        ('Devices');
--
-- insert into books_categories (book_id, category_id)
-- values (1, 1),
--        (2, 1),
--        (3, 1),
--        (4, 2);

insert into roles (privilege)
values ('ROLE_USER'),
       ('ROLE_MANAGER'),
       ('ROLE_ADMIN');

insert into users (phone, email, password, first_name, last_name)
values ('12345678', 'admin@gmail.com', '$2a$10$FlpY8S3SIerY1HBe/1zuTevGX1oR1gZ8YMe/4F4qBTJll25ArQDx6', 'First Name',
        'Last Name');

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2),
       (1, 3);