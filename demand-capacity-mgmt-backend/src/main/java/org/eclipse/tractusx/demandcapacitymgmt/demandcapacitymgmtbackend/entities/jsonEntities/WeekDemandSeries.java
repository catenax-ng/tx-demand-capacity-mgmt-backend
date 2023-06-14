package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeekDemandSeries {

    private String expectedSupplierLocation;
}
