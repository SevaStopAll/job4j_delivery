create table IF NOT EXISTS delivery_statuses(
    id serial primary key not null,
    name varchar(2000) not null
);

comment on table delivery_statuses is '������� �������� ������';
comment on column delivery_statuses.id is '������������� �������';
comment on column delivery_statuses.name is '�������� �������';