package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedMaterialDemandRequestDto;

import java.util.List;

public interface WeekBasedMaterialService {
    void createWeekBasedMaterial(List<WeekBasedMaterialDemandRequestDto> weekBasedMaterialDemandRequestDto);

    void sendWeekBasedMaterial();

    void receiveWeekBasedMaterial();
}
