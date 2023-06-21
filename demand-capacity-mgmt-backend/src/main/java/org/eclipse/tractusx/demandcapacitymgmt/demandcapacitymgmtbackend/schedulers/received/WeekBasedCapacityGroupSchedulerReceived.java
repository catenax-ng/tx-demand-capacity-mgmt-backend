package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.schedulers.received;

import lombok.AllArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedCapacityGroupService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeekBasedCapacityGroupSchedulerReceived {

    private final WeekBasedCapacityGroupService weekBasedCapacityGroupService;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void capacityGroupSync() {
        weekBasedCapacityGroupService.receiveWeekBasedCapacityGroup();
    }
}
