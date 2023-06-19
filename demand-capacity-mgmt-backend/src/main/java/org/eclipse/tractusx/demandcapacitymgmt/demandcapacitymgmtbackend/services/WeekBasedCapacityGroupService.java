package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedCapacityGroupRequest;

public interface WeekBasedCapacityGroupService {

    void createWeekBasedCapacityGroup(WeekBasedCapacityGroupRequest weekBasedCapacityGroupRequest);
}
