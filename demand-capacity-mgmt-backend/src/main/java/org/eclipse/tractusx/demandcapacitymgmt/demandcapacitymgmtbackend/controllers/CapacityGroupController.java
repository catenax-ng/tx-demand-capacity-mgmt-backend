package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.CapacityGroupApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CapacityGroupController implements CapacityGroupApi {

    @Override
    public ResponseEntity<MaterialDemandResponse> getCapacityGroup() throws Exception {
        return null;
    }
}
