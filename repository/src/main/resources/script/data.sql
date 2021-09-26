create table gift_certificate
(
    id          bigint auto_increment primary key,
    name        varchar(50) not null,
    description varchar(50) not null,
    price       decimal(10)     not null,
    duration    int         not null,
    created     datetime    not null,
    updated     datetime    not null
);

INSERT INTO gift_certificate (name, description, price, duration, created, updated)
VALUES ('first', 'description', 1000, 90, '2021-09-06 14:54:31', '2021-09-06 14:54:31'),
       ('second', 'description', 1000, 90, '2021-09-06 14:54:31', '2021-09-06 14:54:31'),
       ('third', 'description', 1000, 90, '2021-09-06 14:54:31', '2021-09-06 14:54:31');