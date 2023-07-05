package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.MaterialDemandRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandSeries;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandSeriesValues;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.MaterialDemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.UnitMeasureEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.NotFoundException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.DemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.MaterialDemandEntityRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.CompanyService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.DemandService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.UnityOfMeasureService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DemandServiceImpl implements DemandService {

    private final DemandRepository demandRepository;

    private final CompanyService companyService;

    private final UnityOfMeasureService unityOfMeasureService;

    private final MaterialDemandEntityRepository materialDemandEntityRepository;

    @Override
    public DemandResponseDto createDemand(MaterialDemandRequest materialDemandRequest) {
        validateMaterialDemandRequestFields(materialDemandRequest);

        //materialDemandEntityRepository.save(materialDemandEntity);

        return convertDemandResponseDto(null);
    }

    @Override
    public List<DemandResponseDto> getAllDemandsByProjectId(Long projectId) {
        //todo fix this part
        //        List<DemandEntity> demandEntityList = demandRepository.findAllByProject(null);
        //
        //        return demandEntityList.stream().map(this::convertDemandResponseDto).collect(Collectors.toList());
        return null;
    }

    @Override
    public DemandResponseDto getDemandById(Long demandId) {
        DemandEntity demand = getDemandEntity(demandId);
        return convertDemandResponseDto(demand);
    }

    @Override
    public DemandResponseDto updateDemand(Long demandId, DemandRequestUpdateDto demandRequestUpdateDto) {
        DemandEntity demand = getDemandEntity(demandId);

        demand.setDeliveredValue(demandRequestUpdateDto.getActualDemand().doubleValue());

        demand = demandRepository.save(demand);
        return convertDemandResponseDto(demand);
    }

    @Override
    public void deleteDemandById(Long demandId) {
        DemandEntity demand = getDemandEntity(demandId);

        demandRepository.delete(demand);
    }

    private DemandEntity getDemandEntity(Long demandId) {
        Optional<DemandEntity> demand = demandRepository.findById(demandId);

        if (demand.isEmpty()) {
            throw new NotFoundException("");
        }

        return demand.get();
    }

    private DemandResponseDto convertDemandResponseDto(DemandEntity demandEntity) {
        DemandResponseDto responseDto = new DemandResponseDto();
        responseDto.setId(demandEntity.getId().toString());
        responseDto.setDescription(demandEntity.getDescription());
        responseDto.setStartDate(demandEntity.getStartDate().toString());
        responseDto.setEndDate(demandEntity.getEndDate().toString());
        responseDto.setMaximumValue(BigDecimal.valueOf(demandEntity.getMaximumValue()));
        responseDto.setRequiredValue(BigDecimal.valueOf(demandEntity.getRequiredValue()));
        responseDto.setCompanyId(demandEntity.getCompany().getId().toString());
        responseDto.setDeliveredValue(BigDecimal.valueOf(demandEntity.getDeliveredValue()));

        return responseDto;
    }

    private void validateMaterialDemandRequestFields(MaterialDemandRequest materialDemandRequest) {
        if (!UUIDUtil.checkValidUUID(materialDemandRequest.getCustomerId())) {
            throw new BadRequestException("not a valid ID");
        }

        if (!UUIDUtil.checkValidUUID(materialDemandRequest.getSupplierId())) {
            throw new BadRequestException("not a valid ID");
        }
    }

    private void convertDtoToEntity(MaterialDemandRequest materialDemandRequest) {
        CompanyEntity supplierEntity = companyService.getCompanyById(
            UUIDUtil.generateUUIDFromString(materialDemandRequest.getSupplierId())
        );

        CompanyEntity customerEntity = companyService.getCompanyById(
            UUIDUtil.generateUUIDFromString(materialDemandRequest.getSupplierId())
        );

        UnitMeasureEntity unitMeasure = unityOfMeasureService.findById(
            UUID.fromString(materialDemandRequest.getUnitMeasureId())
        );

        DemandSeriesValues demandSeriesValues = DemandSeriesValues
            .builder()
            .calendarWeek(LocalDateTime.now())
            .demand(100d)
            .build();

        List<DemandSeriesValues> demandSeriesValuesList = List.of(demandSeriesValues);

        DemandSeries demandSeries = DemandSeries.builder().demandSeriesValues(demandSeriesValuesList).build();

        MaterialDemandEntity materialDemandEntity = MaterialDemandEntity
            .builder()
            .id(UUID.randomUUID())
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
}
