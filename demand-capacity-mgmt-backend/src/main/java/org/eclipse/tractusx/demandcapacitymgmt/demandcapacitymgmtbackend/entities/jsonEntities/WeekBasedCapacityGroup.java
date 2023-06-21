package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeekBasedCapacityGroup {

    private String unityOfMeasure;
    private String supplier;
    private String name;
    private List<String> supplierLocations;
    private String changedAt;
    private String capacityGroupId;
    private String customer;
    private List<Capacity> capacities;
    private List<LikedDemandSeries> likedDemandSeries;
}