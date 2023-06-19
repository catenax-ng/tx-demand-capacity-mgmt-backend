package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.DemandApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
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
    public ResponseEntity<Void> deleteDemandsById(String demandId) throws Exception {
        demandService.deleteDemandById(Long.parseLong(demandId));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<DemandResponseDto> getDemandsById(String demandId) {
        DemandResponseDto responseDto = demandService.getDemandById(Long.parseLong(demandId));
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Override
    public ResponseEntity<List<DemandResponseDto>> getDemandsByProjectID(String projectId) {
        List<DemandResponseDto> demandResponseDtos = demandService.getAllDemandsByProjectId(Long.parseLong(projectId));
        return ResponseEntity.status(HttpStatus.OK).body(demandResponseDtos);
    }

    @Override
    public ResponseEntity<DemandResponseDto> postDemand(DemandRequestDto demandRequestDto) {
        DemandResponseDto responseDto = demandService.createDemand(demandRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Override
    public ResponseEntity<DemandResponseDto> updateDemandsById(
        String demandId,
        DemandRequestUpdateDto demandRequestUpdateDto
    ) {
        DemandResponseDto responseDto = demandService.updateDemand(Long.parseLong(demandId), demandRequestUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
