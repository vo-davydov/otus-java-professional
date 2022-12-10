create table address
(
    id   bigint not null primary key,
    street varchar(50)
);

create table phones
(
    id   bigint not null primary key,
    number varchar(50),
    client_id bigint
);

ALTER TABLE client
    ADD COLUMN address_id bigint;

ALTER TABLE client
    ADD CONSTRAINT ADDRESS_ID_FK
        FOREIGN KEY (address_id) REFERENCES address(ID);

ALTER TABLE phones
    ADD CONSTRAINT CLIENT_ID_FK
        FOREIGN KEY (client_id) REFERENCES client(ID);