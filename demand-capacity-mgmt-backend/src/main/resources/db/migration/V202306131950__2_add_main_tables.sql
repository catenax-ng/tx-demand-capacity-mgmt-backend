create table week_based_material_demand
(
    id     integer constraint week_based_material_demand_pk primary key,
    data   jsonb,
    viewed boolean
);


create table week_based_capacity
(
    id     integer constraint week_based_capacity_pk primary key,
    data   jsonb,
    viewed boolean
);