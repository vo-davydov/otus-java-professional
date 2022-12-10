create table sys_user
(
    id   bigint not null primary key,
    login varchar(50),
    password varchar(50),
    name varchar(50)
);

insert into sys_user (id, login, password, name) values (1, 'admin', 'adminka', 'admin');