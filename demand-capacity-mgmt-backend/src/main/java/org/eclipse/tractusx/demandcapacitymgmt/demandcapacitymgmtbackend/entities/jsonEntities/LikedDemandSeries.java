package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikedDemandSeries {

    private String materialNumberCustomer;
    private String materialNumberSupplier;
    private String customerLocation;
    private DemandCategory demandCategory;

}
