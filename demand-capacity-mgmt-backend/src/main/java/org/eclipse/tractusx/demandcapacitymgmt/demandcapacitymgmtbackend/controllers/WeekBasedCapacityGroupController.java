package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.WeekBasedCapacityGroupApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedCapacityGroupRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeekBasedCapacityGroupController implements WeekBasedCapacityGroupApi {

    @Override
    public ResponseEntity<Void> postWeekBasedCapacityGroup(
        WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest
    ) {
        return null;
    }
}
