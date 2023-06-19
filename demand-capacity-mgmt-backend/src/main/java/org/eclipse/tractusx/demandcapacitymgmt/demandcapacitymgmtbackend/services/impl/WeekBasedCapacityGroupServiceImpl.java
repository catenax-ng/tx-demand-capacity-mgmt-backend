package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedCapacityGroupRequest;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedCapacityGroupService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@Service
public class WeekBasedCapacityGroupServiceImpl implements WeekBasedCapacityGroupService{
    @Override
    public void createWeekBasedCapacityGroup(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest) {

        validateFields(weekBasedCapacityGroupRequest);

    }


    private void validateFields(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest) {

        if(!UUIDUtil.checkValidUUID(weekBasedCapacityGroupRequest.getCapacityGroupId())){
            throw new BadRequestException("not a ID");
        }

    }
}
