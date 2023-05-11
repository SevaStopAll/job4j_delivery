create table IF NOT EXISTS deliveries(
    id serial primary key not null,
    address varchar(2000),
    status int not null references delivery_statuses(id) default 1,
    price integer,
    payment_method varchar(256)
);

comment on table deliveries is '������� ��������';
comment on column deliveries.id is '������������� ��������';
comment on column deliveries.address is '����� ��������';
comment on column deliveries.status is '������ ��������';
comment on column deliveries.price is '��������� ������������� ������';
comment on column deliveries.payment_method is '������ ������ ������';