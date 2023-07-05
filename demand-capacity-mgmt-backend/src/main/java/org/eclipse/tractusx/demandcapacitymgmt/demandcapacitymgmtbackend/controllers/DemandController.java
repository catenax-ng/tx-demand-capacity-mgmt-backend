package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.DemandApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandRequest;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.DemandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DemandController implements DemandApi {

    private final DemandService demandService;

    @Override
    public ResponseEntity<Void> deleteDemandsById(String demandId) {
        demandService.deleteDemandById(demandId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<MaterialDemandResponse> getDemandsById(String demandId) {
        MaterialDemandResponse responseDto = demandService.getDemandById(demandId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Override
    public ResponseEntity<List<MaterialDemandResponse>> getDemandsByProjectID() {
        List<MaterialDemandResponse> demandResponseDtos = demandService.getAllDemandsByProjectId();
        return ResponseEntity.status(HttpStatus.OK).body(demandResponseDtos);
    }

    @Override
    public ResponseEntity<MaterialDemandResponse> postDemand(MaterialDemandRequest materialDemandRequest) {
        MaterialDemandResponse responseDto = demandService.createDemand(materialDemandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Override
    public ResponseEntity<MaterialDemandResponse> updateDemandsById(
        String demandId,
        DemandRequestUpdateDto demandRequestUpdateDto
    ) {
        MaterialDemandResponse responseDto = demandService.updateDemand(demandId, demandRequestUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
