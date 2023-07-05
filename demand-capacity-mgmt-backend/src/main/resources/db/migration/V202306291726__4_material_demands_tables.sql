CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table demand_category
(
    id uuid DEFAULT uuid_generate_v4() primary key ,
    demand_category_code  varchar(400),
    demand_category_name varchar(400)
);

create table unity_of_measure
(
    id  uuid constraint unity_of_measure_pk primary key,
    code_value  varchar(400) ,
    display_value varchar(400)
);

create table company_base_data
(
    id  uuid constraint company_base_data_pk primary key,
    bpn varchar(400),
    company_name varchar(400),
    street varchar(400),
    number varchar(400),
    zip_code varchar(400),
    country varchar(400),
    my_company varchar(400),
    edc_url varchar(400)
);

create table material_demand
(
    id uuid DEFAULT uuid_generate_v4() primary key,
    material_description_customer varchar(400),
    material_number_customer varchar(400),
    material_number_supplier varchar(400),
    changed_at timestamp,
    customer_id uuid constraint customer_id references company_base_data(id),
    supplier_id uuid constraint supplier_id references company_base_data(id),
    unity_of_measure_id uuid constraint unity_of_measure_id references unity_of_measure(id)
);

create table demand_series
(
    id uuid DEFAULT uuid_generate_v4() primary key,
    material_demand_id uuid constraint material_demand_id references material_demand(id),
    customer_location_id uuid constraint customer_location_id references company_base_data(id),
    expected_supplier_location_id varchar(720),
    demand_category_code_id uuid constraint demand_category_code_id references demand_category(id)
);

create table demand_series_values
(
    id uuid DEFAULT uuid_generate_v4() primary key,
    demand_series_id uuid constraint demand_series_id references demand_series(id),
    calendar_week timestamp not null,
    demand numeric

)