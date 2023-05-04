create table IF NOT EXISTS delivery_statuses(
    id serial primary key not null,
    name varchar(2000) not null
);