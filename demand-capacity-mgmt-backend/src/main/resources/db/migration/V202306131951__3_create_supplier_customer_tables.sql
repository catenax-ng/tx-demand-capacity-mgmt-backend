create table supplier
(
    id  integer constraint supplier_pk primary key,
    name varchar(400),
    legal_name varchar(400),
    edc_url varchar(400),
    viewed boolean
);

create table customer
(
    id  integer constraint customer_pk primary key,
    name varchar(400),
    legal_name varchar(400),
    edc_url varchar(400),
    viewed boolean
);