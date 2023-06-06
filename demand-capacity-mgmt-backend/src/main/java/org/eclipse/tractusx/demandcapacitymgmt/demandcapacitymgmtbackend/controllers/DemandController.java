package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.DemandApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DemandController implements DemandApi {
    @Override
    public ResponseEntity<DemandResponseDto> getDemandsById(String demandId) {
        return null;
    }

    @Override
    public ResponseEntity<List<DemandRequestDto>> getDemandsByProjectID(String projectId) {
        return null;
    }

    @Override
    public ResponseEntity<DemandRequestDto> postDemand(DemandRequestDto demandRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<DemandResponseDto> updateDemandsById(String demandId, DemandRequestUpdateDto demandRequestUpdateDto) {
        return null;
    }
}
