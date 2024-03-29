/*
 * *******************************************************************************
 *   Copyright (c) 2023 BMW AG
 *   Copyright (c) 2023 Contributors to the Eclipse Foundation
 *
 *   See the NOTICE file(s) distributed with this work for additional
 *   information regarding copyright ownership.
 *
 *   This program and the accompanying materials are made available under the
 *   terms of the Apache License, Version 2.0 which is available at
 *   https://www.apache.org/licenses/LICENSE-2.0.
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *   WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *   License for the specific language governing permissions and limitations
 *   under the License.
 *
 *   SPDX-License-Identifier: Apache-2.0
 *   ********************************************************************************
 *
 */

package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.CompanyDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandRequest;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandResponse;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandSeries;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandSeriesResponse;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandSeriesValue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandCategoryEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandSeriesValues;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.MaterialDemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.UnitMeasureEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.NotFoundException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.MaterialDemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.CompanyService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.DemandCategoryService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.DemandService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.UnityOfMeasureService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.DataConverterUtil;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DemandServiceImpl implements DemandService {

    private final CompanyService companyService;

    private final UnityOfMeasureService unityOfMeasureService;

    private final MaterialDemandRepository materialDemandRepository;

    private final DemandCategoryService demandCategoryService;

    @Override
    public MaterialDemandResponse createDemand(MaterialDemandRequest materialDemandRequest) {
        validateMaterialDemandRequestFields(materialDemandRequest);

        MaterialDemandEntity materialDemandEntity = convertDtoToEntity(materialDemandRequest);

        materialDemandEntity = materialDemandRepository.save(materialDemandEntity);

        return convertDemandResponseDto(materialDemandEntity);
    }

    @Override
    public List<MaterialDemandResponse> getAllDemandsByProjectId() {
        List<MaterialDemandEntity> demandEntityList = materialDemandRepository.findAll();

        return demandEntityList.stream().map(this::convertDemandResponseDto).toList();
    }

    @Override
    public MaterialDemandResponse getDemandById(String demandId) {
        MaterialDemandEntity demand = getDemandEntity(demandId);
        return convertDemandResponseDto(demand);
    }

    @Override
    public MaterialDemandResponse updateDemand(String demandId, DemandRequestUpdateDto demandRequestUpdateDto) {
        MaterialDemandEntity demand = getDemandEntity(demandId);

        demand = materialDemandRepository.save(demand);
        return convertDemandResponseDto(demand);
    }

    @Override
    public void deleteDemandById(String demandId) {
        MaterialDemandEntity demand = getDemandEntity(demandId);

        materialDemandRepository.delete(demand);
    }

    private MaterialDemandEntity getDemandEntity(String demandId) {
        UUIDUtil.checkValidUUID(demandId);
        UUID uuid = UUIDUtil.generateUUIDFromString(demandId);
        Optional<MaterialDemandEntity> demand = materialDemandRepository.findById(uuid);

        if (demand.isEmpty()) {
            throw new NotFoundException("");
        }

        return demand.get();
    }

    private MaterialDemandResponse convertDemandResponseDto(MaterialDemandEntity materialDemandEntity) {
        MaterialDemandResponse responseDto = new MaterialDemandResponse();

        CompanyDto customer = companyService.convertEntityToDto(materialDemandEntity.getCustomerId());
        CompanyDto supplier = companyService.convertEntityToDto(materialDemandEntity.getSupplierId());

        responseDto.setMaterialDescriptionCustomer(materialDemandEntity.getMaterialDescriptionCustomer());
        responseDto.setMaterialNumberCustomer(materialDemandEntity.getMaterialNumberCustomer());
        responseDto.setMaterialNumberSupplier(materialDemandEntity.getMaterialNumberSupplier());
        responseDto.setCustomer(customer);
        responseDto.setSupplier(supplier);
        responseDto.setChangedAt(materialDemandEntity.getChangedAt().toString());

        MaterialDemandSeriesResponse materialDemandSeriesResponse = enrichMaterialDemandSeriesResponse(
            materialDemandEntity.getDemandSeries()
        );
        responseDto.setDemandSeries(materialDemandSeriesResponse);

        return responseDto;
    }

    private void validateMaterialDemandRequestFields(MaterialDemandRequest materialDemandRequest) {
        if (!UUIDUtil.checkValidUUID(materialDemandRequest.getCustomerId())) {
            throw new BadRequestException("not a valid ID");
        }

        if (!UUIDUtil.checkValidUUID(materialDemandRequest.getSupplierId())) {
            throw new BadRequestException("not a valid ID");
        }

        if (!UUIDUtil.checkValidUUID(materialDemandRequest.getMaterialDemandSeries().getCustomerLocationId())) {
            throw new BadRequestException("not a valid ID");
        }

        if (!UUIDUtil.checkValidUUID(materialDemandRequest.getMaterialDemandSeries().getDemandCategoryId())) {
            throw new BadRequestException("not a valid category");
        }

        List<LocalDateTime> dates = materialDemandRequest
            .getMaterialDemandSeries()
            .getDemandSeriesValues()
            .stream()
            .map(
                materialDemandSeriesValue ->
                    DataConverterUtil.convertFromString(materialDemandSeriesValue.getCalendarWeek())
            )
            .toList();

        if (!DataConverterUtil.checkListAllMonday(dates) || !DataConverterUtil.checkDatesSequence(dates)) {
            throw new BadRequestException("not a valid dates");
        }

        materialDemandRequest
            .getMaterialDemandSeries()
            .getExpectedSupplierLocationId()
            .forEach(UUIDUtil::checkValidUUID);

        List<UUID> expectedSuppliersLocation = materialDemandRequest
            .getMaterialDemandSeries()
            .getExpectedSupplierLocationId()
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
    }

    private MaterialDemandEntity convertDtoToEntity(MaterialDemandRequest materialDemandRequest) {
        UUID materialDemandId = UUID.randomUUID();
        UUID demandSeriesId = UUID.randomUUID();

        CompanyEntity supplierEntity = companyService.getCompanyById(
            UUIDUtil.generateUUIDFromString(materialDemandRequest.getSupplierId())
        );

        CompanyEntity customerEntity = companyService.getCompanyById(
            UUIDUtil.generateUUIDFromString(materialDemandRequest.getSupplierId())
        );

        UnitMeasureEntity unitMeasure = unityOfMeasureService.findById(
            UUID.fromString(materialDemandRequest.getUnitMeasureId())
        );

        DemandCategoryEntity demandCategory = demandCategoryService.findById(
            UUIDUtil.generateUUIDFromString(materialDemandRequest.getMaterialDemandSeries().getDemandCategoryId())
        );

        DemandSeries demandSeries = enrichDemandSeries(
            materialDemandRequest.getMaterialDemandSeries(),
            customerEntity,
            demandCategory
        );

        return MaterialDemandEntity
            .builder()
            .materialDescriptionCustomer(materialDemandRequest.getMaterialDescriptionCustomer())
            .materialNumberCustomer(materialDemandRequest.getMaterialNumberCustomer())
            .materialNumberSupplier("")
            .customerId(customerEntity)
            .supplierId(supplierEntity)
            .unitMeasure(unitMeasure)
            .demandSeries(demandSeries)
            .changedAt(LocalDateTime.now())
            .build();
    }

    private DemandSeries enrichDemandSeries(
        MaterialDemandSeries materialDemandSeries,
        CompanyEntity customerEntity,
        DemandCategoryEntity demandCategory
    ) {
        List<DemandSeriesValues> demandSeriesValues = enrichDemandSeriesValues(
            materialDemandSeries.getDemandSeriesValues()
        );

        return DemandSeries
            .builder()
            .expectedSupplierLocation(materialDemandSeries.getExpectedSupplierLocationId())
            .customerLocation(customerEntity)
            .demandCategory(demandCategory)
            .demandSeriesValues(demandSeriesValues)
            .build();
    }

    private List<DemandSeriesValues> enrichDemandSeriesValues(List<MaterialDemandSeriesValue> demandSeriesValues) {
        return demandSeriesValues
            .stream()
            .map(
                materialDemandSeriesValue ->
                    DemandSeriesValues
                        .builder()
                        .demand(materialDemandSeriesValue.getDemand().doubleValue())
                        .calendarWeek(
                            DataConverterUtil.convertFromString(materialDemandSeriesValue.getCalendarWeek().toString())
                        )
                        .build()
            )
            .toList();
    }

    private MaterialDemandSeriesResponse enrichMaterialDemandSeriesResponse(DemandSeries demandSeries) {
        List<UUID> uuidList = demandSeries
            .getExpectedSupplierLocation()
            .stream()
            .map(UUIDUtil::generateUUIDFromString)
            .toList();
        List<CompanyEntity> companyEntities = companyService.getCompanyIn(uuidList);

        List<CompanyDto> expectedSupplierLocation = companyEntities
            .stream()
            .map(companyService::convertEntityToDto)
            .toList();

        CompanyDto customer = companyService.convertEntityToDto(demandSeries.getCustomerLocation());

        MaterialDemandSeriesResponse materialDemandSeriesResponse = new MaterialDemandSeriesResponse();
        materialDemandSeriesResponse.setCustomerLocation(customer);
        materialDemandSeriesResponse.setExpectedSupplierLocation(expectedSupplierLocation);

        List<MaterialDemandSeriesValue> materialDemandSeriesValues = demandSeries
            .getDemandSeriesValues()
            .stream()
            .map(this::enrichMaterialDemandSeriesValue)
            .toList();

        materialDemandSeriesResponse.setDemandSeriesValues(materialDemandSeriesValues);

        return materialDemandSeriesResponse;
    }

    private MaterialDemandSeriesValue enrichMaterialDemandSeriesValue(DemandSeriesValues demandSeriesValues) {
        MaterialDemandSeriesValue materialDemandSeriesValue = new MaterialDemandSeriesValue();
        materialDemandSeriesValue.setDemand(BigDecimal.valueOf(demandSeriesValues.getDemand()));
        materialDemandSeriesValue.setCalendarWeek(demandSeriesValues.getCalendarWeek().toString());

        return materialDemandSeriesValue;
    }
}
