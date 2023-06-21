package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.feignInterfaces;


import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedMaterialDemand;


import java.net.URI;
import java.util.List;

//@FeignClient(name="weekBasedMaterialDemandFeign" , url = "feignUrl")
public interface WeekBasedMaterialDemandFeign {

    //@RequestLine("GET")
    List<WeekBasedMaterialDemand> getAllCustomers(URI baseUri);

    String test(URI baseUri);

}
