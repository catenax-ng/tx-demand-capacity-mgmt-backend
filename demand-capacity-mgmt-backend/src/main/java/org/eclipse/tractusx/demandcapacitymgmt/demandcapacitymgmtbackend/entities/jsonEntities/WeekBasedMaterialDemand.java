package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeekBasedMaterialDemand {

    private String unityOfMeasure;
    private String materialDescriptionCustomer;
    private String materialDemandId;
    private String materialNumberSupplier;
    private String supplier;
    private String changedAt;
    private String materialNumberCustomer;
    private String customer;
    private List<WeekDemandSeries> demandSeries;
}
