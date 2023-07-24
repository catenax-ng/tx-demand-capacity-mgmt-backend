package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.CapacityGroupRequest;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.CapacityRequest;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.LinkDemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.CapacityGroupRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.LinkDemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl.CapacityGroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CapacityGroupServiceTest {

    @InjectMocks
    private CapacityGroupServiceImpl capacityGroupService;

    @Mock
    private CompanyService companyService;

    @Mock
    private UnityOfMeasureService unityOfMeasureService;

    @Mock
    private CapacityGroupRepository capacityGroupRepository;

    @Mock
    private LinkDemandRepository linkDemandRepository;


    private static CapacityGroupRequest capacityGroupRequest = createCapacityGroupRequest();

    private static CompanyEntity company = createCompanyEntity();

    private LinkDemandEntity linkDemandEntity = createLinkDemandEntity();

    @Test
    void shouldCreateCapacityGroup(){

        when(companyService.getCompanyIn(any())).thenReturn(List.of(company));
        when(unityOfMeasureService.findById(any())).thenReturn(null);
        when(companyService.getCompanyById(any())).thenReturn(company);
        when(linkDemandRepository.findById(any())).thenReturn(Optional.of(linkDemandEntity));

        capacityGroupService.createCapacityGroup(capacityGroupRequest);

        verify(capacityGroupRepository, times(1)).save(any());
    }


    private static CapacityGroupRequest createCapacityGroupRequest(){

        CapacityGroupRequest capacityGroupRequest = new CapacityGroupRequest();

        CapacityRequest capacityRequest = new CapacityRequest();
        capacityRequest.setActualCapacity(BigDecimal.valueOf(20));
        capacityRequest.setMaximumCapacity(BigDecimal.valueOf(20));
        capacityRequest.setCalendarWeek("2023-06-19");

        //08b95a75-11a7-4bea-a958-821b9cb01641
        capacityGroupRequest.setCustomer("08b95a75-11a7-4bea-a958-821b9cb01641");
        capacityGroupRequest.setSupplier("08b95a75-11a7-4bea-a958-821b9cb01641");
        capacityGroupRequest.setName("Test");
        capacityGroupRequest.setUnitOfMeasure("529f760c-71f4-4d0d-a924-7e4a5b645c5e");
        capacityGroupRequest.setLinkedDemandSeries(List.of("e6ee8fc4-60e5-4af8-b878-e87105d834f2"));
        capacityGroupRequest.setSupplierLocations(List.of("08b95a75-11a7-4bea-a958-821b9cb01641"));
        capacityGroupRequest.setCapacities(List.of(capacityRequest));

        return capacityGroupRequest;

    }

    private static CompanyEntity createCompanyEntity(){
        return CompanyEntity
                .builder()
                .id(UUID.fromString("08b95a75-11a7-4bea-a958-821b9cb01641"))
                .myCompany("Test")
                .companyName("Test")
                .build();
    }

    private static LinkDemandEntity createLinkDemandEntity(){
        return LinkDemandEntity
                .builder()
                .linked(false)
                .demandCategoryId("")
                .materialNumberSupplier("")
                .materialNumberCustomer("")
                .build();
    }


}
