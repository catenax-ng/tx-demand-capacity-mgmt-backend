package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandRequest;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandResponse;
import java.util.List;

public interface DemandService {
    MaterialDemandResponse createDemand(MaterialDemandRequest materialDemandRequest);

    List<MaterialDemandResponse> getAllDemandsByProjectId();

    MaterialDemandResponse getDemandById(String demandId);

    MaterialDemandResponse updateDemand(String demandId, DemandRequestUpdateDto demandRequestUpdateDto);

    void deleteDemandById(String demandId);
}
