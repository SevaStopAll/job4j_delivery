create table IF NOT EXISTS deliveries(
    id serial primary key not null,
    address varchar(2000),
    status int not null references status(id) default 1
);