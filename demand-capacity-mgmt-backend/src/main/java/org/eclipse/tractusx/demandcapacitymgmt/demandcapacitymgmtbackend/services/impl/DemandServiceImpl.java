package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestUpdateDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.ProjectEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.NotFoundException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.DemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.DemandService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.ProjectService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.DataConverterUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DemandServiceImpl implements DemandService {

    private final DemandRepository demandRepository;

    private final ProjectService projectService;

    private final CompanyServiceImpl companyService;

    @Override
    public DemandResponseDto createDemand(DemandRequestDto demandRequestDto) {
        ProjectEntity project = projectService.getProjectEntityById(Long.parseLong(demandRequestDto.getProjectId()));

        CompanyEntity company = companyService.getCompanyById(Long.parseLong(demandRequestDto.getCompanyId()));

        DemandEntity demandEntity = DemandEntity
            .builder()
            .company(company)
            .project(project)
            .requiredValue(demandRequestDto.getRequiredValue().doubleValue())
            .deliveredValue(demandRequestDto.getDeliveredValue().doubleValue())
            .maximumValue(demandRequestDto.getMaximumValue().doubleValue())
            .description(demandRequestDto.getDescription())
            .demandCategory(demandRequestDto.getDemandCategory())
            .startDate(DataConverterUtil.convertFromString(demandRequestDto.getStartDate()))
            .endDate(DataConverterUtil.convertFromString(demandRequestDto.getEndDate()))
            .build();

        demandEntity = demandRepository.save(demandEntity);

        return convertDemandResponseDto(demandEntity);
    }

    @Override
    public List<DemandResponseDto> getAllDemandsByProjectId(Long projectId) {
        ProjectEntity project = projectService.getProjectEntityById(projectId);

        List<DemandEntity> demandEntityList = demandRepository.findAllByProject(project);

        return demandEntityList.stream().map(this::convertDemandResponseDto).collect(Collectors.toList());
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
}
