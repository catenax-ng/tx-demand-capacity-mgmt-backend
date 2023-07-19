package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.CapacityGroupRequest;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CapacityGroupEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CapacityTimeSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.LinkDemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.LinkedDemandSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.UnitMeasureEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.CapacityGroupRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.LinkDemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.CapacityGroupService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.CompanyService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.UnityOfMeasureService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.DataConverterUtil;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CapacityGroupServiceImpl implements CapacityGroupService {

    private final CompanyService companyService;

    private final UnityOfMeasureService unityOfMeasureService;

    private final CapacityGroupRepository capacityGroupRepository;

    private final LinkDemandRepository linkDemandRepository;

    @Override
    public void createCapacityGroup(CapacityGroupRequest capacityGroupRequest) {
        validateRequestFields(capacityGroupRequest);

        List<CapacityGroupEntity> capacityGroupEntity = enrichCapacityGroup(capacityGroupRequest);

        saveAll(capacityGroupEntity);
    }

    private void validateRequestFields(CapacityGroupRequest capacityGroupRequest) {
        if (!UUIDUtil.checkValidUUID(capacityGroupRequest.getCustomer())) {
            throw new BadRequestException("not a valid ID");
        }

        if (!UUIDUtil.checkValidUUID(capacityGroupRequest.getSupplier())) {
            throw new BadRequestException("not a valid ID");
        }

        capacityGroupRequest.getSupplierLocations().forEach(UUIDUtil::checkValidUUID);

        List<UUID> expectedSuppliersLocation = capacityGroupRequest
            .getSupplierLocations()
            .stream()
            .map(UUIDUtil::generateUUIDFromString)
            .toList();

        List<CompanyEntity> companyEntities = companyService.getCompanyIn(expectedSuppliersLocation);

        boolean hasAllCompanies = companyEntities
            .stream()
            .map(CompanyEntity::getId)
            .allMatch(expectedSuppliersLocation::contains);

        if (!hasAllCompanies) {
            throw new BadRequestException("Some Invalid Company");
        }

        capacityGroupRequest.getCapacities().forEach(capacityLink -> {

            List<LocalDateTime> dates = capacityLink.getCapacities().stream()
                        .map(capacityResponse -> DataConverterUtil.convertFromString(capacityResponse.getCalendarWeek()))
                        .toList();

            if (!DataConverterUtil.checkListAllMonday(dates) || !DataConverterUtil.checkDatesSequence(dates)) {
                            throw new BadRequestException("not a valid dates");
            }

        });

    }

    private List<CapacityGroupEntity> enrichCapacityGroup(CapacityGroupRequest capacityGroupRequest) {

        UUID capacityGroupId = UUID.randomUUID();
        UnitMeasureEntity unitMeasure = unityOfMeasureService.findById(
            UUIDUtil.generateUUIDFromString(capacityGroupRequest.getUnitOfMeasure())
        );

        CompanyEntity supplier = companyService.getCompanyById(
            UUIDUtil.generateUUIDFromString(capacityGroupRequest.getSupplier())
        );

        CompanyEntity customer = companyService.getCompanyById(
            UUIDUtil.generateUUIDFromString(capacityGroupRequest.getSupplier())
        );

        List<CapacityGroupEntity> capacityGroupEntityList = new LinkedList<>();

        capacityGroupRequest
            .getCapacities()
            .forEach(
                capacityLink -> {
                    LinkDemandEntity linkDemandEntity = linkDemandRepository
                        .findById(UUIDUtil.generateUUIDFromString(capacityLink.getLinkedMaterial()))
                        .orElseThrow();

                    List<CapacityTimeSeries> capacityTimeSeries = capacityLink
                        .getCapacities()
                        .stream()
                        .map(
                            capacityResponse ->
                                enrichCapacityTimeSeries(
                                    DataConverterUtil.convertFromString(capacityResponse.getCalendarWeek()),
                                    capacityResponse.getActualCapacity().doubleValue(),
                                    capacityResponse.getMaximumCapacity().doubleValue()
                                )
                        )
                        .toList();

                    LinkedDemandSeries linkedDemandSeries = LinkedDemandSeries
                        .builder()
                        .materialNumberSupplier(linkDemandEntity.getMaterialNumberSupplier())
                        .materialNumberCustomer(linkDemandEntity.getMaterialNumberCustomer())
                        .build();

                    CapacityGroupEntity capacityGroupEntity = CapacityGroupEntity
                        .builder()
                        .id(UUID.randomUUID())
                        .capacityGroupId(capacityGroupId)
                        .supplierId(supplier)
                        .customerId(customer)
                        .unitMeasure(unitMeasure)
                        .changedAt(LocalDateTime.now())
                        .capacityTimeSeries(capacityTimeSeries)
                        .linkedDemandSeries(List.of(linkedDemandSeries))
                        .name(capacityGroupRequest.getName())
                        .build();

                    capacityGroupEntityList.add(capacityGroupEntity);
                }
            );

        return capacityGroupEntityList;
    }

    private CapacityTimeSeries enrichCapacityTimeSeries(
        LocalDateTime calendarWeek,
        Double actualCapacity,
        Double maximumCapacity
    ) {
        return CapacityTimeSeries
            .builder()
            .id(UUID.randomUUID())
            .calendarWeek(calendarWeek)
            .actualCapacity(actualCapacity)
            .maximumCapacity(maximumCapacity)
            .build();
    }

    private void saveAll(List<CapacityGroupEntity> capacityGroupEntity) {
        capacityGroupRepository.saveAll(capacityGroupEntity);
    }
}
