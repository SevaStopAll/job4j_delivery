create table IF NOT EXISTS deliveries(
    id serial primary key not null,
    address varchar(2000),
    status int not null references delivery_statuses(id) default 1,
    price integer,
    payment_method varchar(256)
);

comment on table deliveries is 'Таблица доставок';
comment on column deliveries.id is 'Идентификатор доставки';
comment on column deliveries.address is 'Адрес доставки';
comment on column deliveries.status is 'Статус доставки';
comment on column deliveries.price is 'Стоимость доставляемого заказа';
comment on column deliveries.payment_method is 'Способ оплаты заказа';