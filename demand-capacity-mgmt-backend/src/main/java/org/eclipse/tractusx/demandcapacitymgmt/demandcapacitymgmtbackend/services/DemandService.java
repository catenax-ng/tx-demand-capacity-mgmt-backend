package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import java.util.List;

public interface DemandService {
    DemandResponseDto createDemand(DemandRequestDto demandRequestDto);

    List<DemandResponseDto> getAllDemandsByProjectId(Long projectId);

    DemandResponseDto getDemandById(Long demandId);

    DemandResponseDto updateDemand(Long demandId, DemandRequestUpdateDto demandRequestUpdateDto);
}
