package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import java.util.List;

public interface DemandService {
    void createDemand(DemandRequestDto demandRequestDto) throws Exception;

    List<DemandResponseDto> getAllDemandsByProjectId(Integer projectId);

    DemandResponseDto getDemandById(Long demandId);

    void updateDemand();
}
