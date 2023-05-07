drop table if exists roles;
create table roles
(
    id         int primary key auto_increment,
    privilege  ENUM ('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN') unique not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
