create table message
(
    id      bigserial primary key,
    sender  varchar(128),
    content text
);