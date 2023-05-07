drop table if exists verification_tokens cascade;
create table verification_tokens
(
    id          int primary key auto_increment,
    user_id     int                                 not null,
    token       varchar(255)                        not null,
    expiry_date timestamp                           not null,
    created_at  timestamp default current_timestamp not null,
    updated_at  timestamp default current_timestamp not null,
    foreign key (user_id) references users (id)
);
