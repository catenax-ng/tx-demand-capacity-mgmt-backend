package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.enums;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectRequestDto;

public enum ProjectType {
    DEMAND_CAPACITY,
    MATERIAL_CAPACITY;

    public static ProjectType from(ProjectRequestDto.TypeEnum typeEnum) {
        switch (typeEnum) {
            case MATERIAL_CAPACITY:
                return MATERIAL_CAPACITY;
            default:
                return DEMAND_CAPACITY;
        }
    }
}
