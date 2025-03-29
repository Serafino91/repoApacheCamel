
CREATE SCHEMA IF NOT EXISTS prove;
SET search_path TO prove;

create table if not exists name_address (
    id_name SERIAL PRIMARY KEY,
    name         varchar(255) null,
    house_number varchar(255) null,
    city         varchar(255) null,
    province     varchar(255) null,
    postal_code  varchar(255) null
);