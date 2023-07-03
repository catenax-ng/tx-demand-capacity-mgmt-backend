package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.WeekBasedMaterialDemandApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedMaterialDemandRequestDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedMaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeekBasedMaterialController implements WeekBasedMaterialDemandApi {

    private final WeekBasedMaterialService weekBasedMaterialService;

    @Override
    public ResponseEntity<Void> postWeekBasedMaterialDemand(
        List<WeekBasedMaterialDemandRequestDto> weekBasedMaterialDemandRequestDto
    ) {
        weekBasedMaterialService.createWeekBasedMaterial(weekBasedMaterialDemandRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
