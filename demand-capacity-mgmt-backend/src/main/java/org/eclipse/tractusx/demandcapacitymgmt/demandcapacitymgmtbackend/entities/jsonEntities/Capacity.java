package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Capacity {

    private String calendarWeek;
    private Double actualCapacity;
    private Double maximumCapacity;
}
