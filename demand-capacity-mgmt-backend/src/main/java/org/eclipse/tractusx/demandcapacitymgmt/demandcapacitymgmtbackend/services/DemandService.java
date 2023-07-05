package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandRequest;
import java.util.List;

public interface DemandService {
    DemandResponseDto createDemand(MaterialDemandRequest materialDemandRequest);

    List<DemandResponseDto> getAllDemandsByProjectId(Long projectId);

    DemandResponseDto getDemandById(Long demandId);

    DemandResponseDto updateDemand(Long demandId, DemandRequestUpdateDto demandRequestUpdateDto);

    void deleteDemandById(Long demandId);
}
