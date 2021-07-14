drop table  if exists books cascade ;
create table books (id bigserial, title varchar(255), description varchar (5000), price int);
insert into books
(title,description,price) values
('Book1, BookDesc1, 100'),
('Book2, BookDesc2, 200');