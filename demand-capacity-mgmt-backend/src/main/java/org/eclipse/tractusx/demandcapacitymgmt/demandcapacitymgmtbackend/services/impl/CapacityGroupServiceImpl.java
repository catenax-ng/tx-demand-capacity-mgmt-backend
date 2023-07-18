package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.CapacityGroupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CapacityGroupEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CapacityTimeSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.UnitMeasureEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.CapacityGroupRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.CapacityGroupService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.CompanyService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.UnityOfMeasureService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.DataConverterUtil;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CapacityGroupServiceImpl implements CapacityGroupService {

    private final CompanyService companyService;

    private final UnityOfMeasureService unityOfMeasureService;

    private final CapacityGroupRepository capacityGroupRepository;

    @Override
    public void createCapacityGroup(CapacityGroupRequest capacityGroupRequest) {

        validateRequestFields(capacityGroupRequest);

        CapacityGroupEntity capacityGroupEntity = enrichCapacityGroup(capacityGroupRequest);

        capacityGroupEntity = save(capacityGroupEntity);

    }

    private void validateRequestFields(CapacityGroupRequest capacityGroupRequest){

        if (!UUIDUtil.checkValidUUID(capacityGroupRequest.getCustomer())) {
            throw new BadRequestException("not a valid ID");
        }

        if (!UUIDUtil.checkValidUUID(capacityGroupRequest.getSupplier())) {
            throw new BadRequestException("not a valid ID");
        }

        capacityGroupRequest.getSupplierLocations().forEach(UUIDUtil::checkValidUUID);

        List<UUID> expectedSuppliersLocation = capacityGroupRequest.getSupplierLocations()
                .stream().map(UUIDUtil::generateUUIDFromString).toList();

        List<CompanyEntity> companyEntities = companyService.getCompanyIn(expectedSuppliersLocation);

        boolean hasAllCompanies = companyEntities
                .stream()
                .map(CompanyEntity::getId)
                .allMatch(expectedSuppliersLocation::contains);

        if (!hasAllCompanies) {
            throw new BadRequestException("Some Invalid Company");
        }

        List<LocalDateTime> dates = capacityGroupRequest
                .getCapacities()
                .stream().map(
                        capacityResponse ->
                            DataConverterUtil.convertFromString(capacityResponse.getCalendarWeek())).toList();

        if (!DataConverterUtil.checkListAllMonday(dates) || !DataConverterUtil.checkDatesSequence(dates)) {
            throw new BadRequestException("not a valid dates");
        }

    }

    private CapacityGroupEntity enrichCapacityGroup(CapacityGroupRequest capacityGroupRequest){

        UnitMeasureEntity unitMeasure = unityOfMeasureService.findById(UUIDUtil.generateUUIDFromString(capacityGroupRequest.getUnitOfMeasure()));

        CompanyEntity supplier = companyService.getCompanyById(UUIDUtil.generateUUIDFromString(capacityGroupRequest.getSupplier()));

        CompanyEntity customer = companyService.getCompanyById(UUIDUtil.generateUUIDFromString(capacityGroupRequest.getSupplier()));

        List<CapacityTimeSeries> capacityTimeSeries = capacityGroupRequest.getCapacities()
                .stream()
                .map(capacityResponse -> enrichCapacityTimeSeries(DataConverterUtil.convertFromString(capacityResponse.getCalendarWeek()),
                        capacityResponse.getActualCapacity().doubleValue(),
                        capacityResponse.getMaximumCapacity().doubleValue()))
                .toList();

        return CapacityGroupEntity
                .builder()
                .supplierId(supplier)
                .customerId(customer)
                .unitMeasure(unitMeasure)
                .changedAt(LocalDateTime.now())
                .capacityTimeSeries(capacityTimeSeries)
                .name(capacityGroupRequest.getName())
                .build();
    }

    private CapacityTimeSeries enrichCapacityTimeSeries(LocalDateTime calendarWeek, Double actualCapacity, Double maximumCapacity){

        return CapacityTimeSeries
                .builder()
                .id(UUID.randomUUID())
                .calendarWeek(calendarWeek)
                .actualCapacity(actualCapacity)
                .maximumCapacity(maximumCapacity)
                .build();
    }

    private CapacityGroupEntity save(CapacityGroupEntity capacityGroupEntity){
        return capacityGroupRepository.save(capacityGroupEntity);
    }

}