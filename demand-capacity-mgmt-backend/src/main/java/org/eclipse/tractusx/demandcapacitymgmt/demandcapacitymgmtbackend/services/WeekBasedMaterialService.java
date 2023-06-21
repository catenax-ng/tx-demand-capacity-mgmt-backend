package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedMaterialDemandRequestDto;

public interface WeekBasedMaterialService {
    void createWeekBasedMaterial(WeekBasedMaterialDemandRequestDto weekBasedMaterialDemandRequestDto);

    void sendWeekBasedMaterial();

    void receiveWeekBasedMaterial();
}
