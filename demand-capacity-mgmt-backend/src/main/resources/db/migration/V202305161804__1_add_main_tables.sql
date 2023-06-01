create table project
(
    id  integer  not null constraint id_pk primary key,
    name         varchar(400),
    initial_date timestamp not null,
    final_date   timestamp,
    type         varchar(10)
);

create table supplier
(
    id   integer constraint supplier_pk primary key,
    name varchar(400)
);

create table demand
(
    id integer not null constraint demand_pk primary key,
    project_id  integer constraint project_id references project(id),
    supplier_id  integer constraint supplier_id references supplier(id),
    required_capacity numeric,
    actual_demand     numeric,
    demand_category varchar(50),
    unit_measure_id integer constraint unity_measure_id references unity_measure(id),
    description varchar(400)
);

create table unity_measure
(
    id   integer constraint unity_measure_id primary key,
    un varchar(3),
    name varchar(40)
);





