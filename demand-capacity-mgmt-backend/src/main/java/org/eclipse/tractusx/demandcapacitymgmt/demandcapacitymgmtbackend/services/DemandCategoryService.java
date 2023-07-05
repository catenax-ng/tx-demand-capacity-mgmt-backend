package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandCategoryResponse;
import java.util.UUID;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandCategoryEntity;

public interface DemandCategoryService {
    DemandCategoryEntity findById(UUID id);

    DemandCategoryResponse convertEntityToDto(DemandCategoryEntity demandCategory);
}
