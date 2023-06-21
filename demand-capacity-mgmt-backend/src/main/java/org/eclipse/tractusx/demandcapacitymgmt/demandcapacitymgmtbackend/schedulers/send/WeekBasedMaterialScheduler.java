package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.schedulers.send;

import lombok.AllArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.WeekBasedMaterialService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@AllArgsConstructor
public class WeekBasedMaterialScheduler {

    private final WeekBasedMaterialService weekBasedMaterialService;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void materialDemandSync() {

        weekBasedMaterialService.sendWeekBasedMaterial();
    }
}
