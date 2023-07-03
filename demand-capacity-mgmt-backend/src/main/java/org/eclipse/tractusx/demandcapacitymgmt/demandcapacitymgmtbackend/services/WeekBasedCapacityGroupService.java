package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.WeekBasedCapacityGroupRequest;
import java.util.List;

public interface WeekBasedCapacityGroupService {
    void createWeekBasedCapacityGroup(List<WeekBasedCapacityGroupRequest> weekBasedCapacityGroupRequest);

    void receiveWeekBasedCapacityGroup();

    void sendWeekBasedCapacityGroup();
}
