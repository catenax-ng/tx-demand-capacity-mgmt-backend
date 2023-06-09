package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandResponseDto;
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
    private final DataConverterUtil dataConverterUtil = new DataConverterUtil();

    private final ProjectService projectService;

    private final CompanyServiceImpl companyService;

    @Override
    public void createDemand(DemandRequestDto demandRequestDto) throws Exception {
        ProjectEntity project = projectService.getProjectEntityById();

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
            .startDate(dataConverterUtil.convertFromString(demandRequestDto.getStartDate()))
            .endDate(dataConverterUtil.convertFromString(demandRequestDto.getEndDate()))
            //.unitMeasure()
            .build();

        demandRepository.save(demandEntity);
    }

    @Override
    public List<DemandResponseDto> getAllDemandsByProjectId(Integer projectId) {
        ProjectEntity project = projectService.getProjectEntityById();

        List<DemandEntity> demandEntityList = demandRepository.findAllByProject(project);

        return demandEntityList
            .stream()
            .map(
                demandEntity -> {
                    DemandResponseDto responseDto = new DemandResponseDto();
                    responseDto.setCompanyId(demandEntity.getCompany().getId().toString());
                    return responseDto;
                }
            )
            .collect(Collectors.toList());
    }

    @Override
    public DemandResponseDto getDemandById(Long demandId) {
        Optional<DemandEntity> demand = demandRepository.findById(demandId);

        if (demand.isEmpty()) {
            throw new NotFoundException("");
        }

        DemandResponseDto responseDto = new DemandResponseDto();

        return responseDto;
    }

    @Override
    public void updateDemand() {}
}
