CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table capacity_group
(
    id uuid DEFAULT uuid_generate_v4() primary key,
    material_description_customer varchar(400),
    material_number_customer varchar(400),
    material_number_supplier varchar(400),
    changed_at timestamp,
    customer_id uuid constraint capacity_group_customer_id references company_base_data(id),
    supplier_id uuid constraint capacity_group_supplier_id references company_base_data(id),
    capacity_group_id uuid constraint customer_id references company_base_data(id),
    unity_of_measure_id uuid constraint unity_of_measure_id references unity_of_measure(id),
    supplier_locations varchar(720)
);

create table capacity_time_series
(
    id uuid DEFAULT uuid_generate_v4() primary key,
    calendar_week timestamp not null,
    actual_capacity numeric,
    maximum_capacity numeric,
    capacity_group_id uuid constraint capacity_group_id references capacity_group(id)
);

create table linked_demand_series
(
    id uuid DEFAULT uuid_generate_v4() primary key,
    demand_category_code_id uuid constraint linked_demand_series_demand_category_code_id references demand_category(id),
    customer_id uuid constraint capacity_group_customer_id references company_base_data(id),
    material_number_customer varchar(400),
    material_number_supplier varchar(400),
    capacity_group_id uuid constraint capacity_group_id references capacity_group(id)
);
