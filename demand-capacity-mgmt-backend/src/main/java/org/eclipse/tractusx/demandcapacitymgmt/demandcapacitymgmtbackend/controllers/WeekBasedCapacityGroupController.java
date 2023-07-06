package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.WeekBasedCapacityGroupApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedCapacityGroupRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedCapacityGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeekBasedCapacityGroupController implements WeekBasedCapacityGroupApi {

    private final WeekBasedCapacityGroupService weekBasedCapacityGroupService;

    /*  @Override
    public ResponseEntity<Void> postWeekBasedCapacityGroup(
        WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest
    ) {
        weekBasedCapacityGroupService.createWeekBasedCapacityGroup(weekBasedCapacityGroupRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }*/

    /*public ResponseEntity<Void> postWeekBasedCapacityGroup(
        List<WeekBasedCapacityGroupRequest> weekBasedCapacityGroupRequest
    ) throws Exception {
        weekBasedCapacityGroupService.createWeekBasedCapacityGroup(weekBasedCapacityGroupRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }*/

    @Override
    public ResponseEntity<Void> postWeekBasedCapacityGroup(
        List<WeekBasedCapacityGroupRequest> weekBasedCapacityGroupRequest
    ) throws Exception {
        return null;
    }
}
