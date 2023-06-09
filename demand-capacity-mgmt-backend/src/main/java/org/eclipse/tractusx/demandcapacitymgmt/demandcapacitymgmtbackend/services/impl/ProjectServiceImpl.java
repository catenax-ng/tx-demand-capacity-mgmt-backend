package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectResponseDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.ProjectEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.enums.ProjectType;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.NotFoundException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.ProjectRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.ProjectService;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.utils.DataConverterUtil;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;


    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        ProjectEntity project = ProjectEntity
            .builder()
            .name(projectRequestDto.getName())
            .initialDate(DataConverterUtil.convertFromString(projectRequestDto.getStartDate()))
            .finalDate(DataConverterUtil.convertFromString(projectRequestDto.getEndDate()))
            .type(ProjectType.from(projectRequestDto.getType()))
            .build();

        project = projectRepository.save(project);

        return generateProjectResponseDto(project);
    }

    @Override
    public void updateProject() {}

    @Override
    public List<ProjectResponseDto> getAllProjects() {
        List<ProjectEntity> projectEntities = projectRepository.findAll();

        return projectEntities.stream().map(this::generateProjectResponseDto).collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto getProjectById() {
        Optional<ProjectEntity> project = projectRepository.findById(1l);

        if (project.isEmpty()) {
            throw new NotFoundException("");
        }

        return generateProjectResponseDto(project.get());
    }

    @Override
    public ProjectEntity getProjectEntityById() {
        Optional<ProjectEntity> project = projectRepository.findById(1l);
        if (project.isEmpty()) {
            throw new NotFoundException("");
        }
        return project.get();
    }

    private ProjectResponseDto generateProjectResponseDto(ProjectEntity project) {
        ProjectResponseDto responseDto = new ProjectResponseDto();
        responseDto.setName(project.getName());
        responseDto.setStartDate(project.getInitialDate().toString());
        responseDto.setEndDate(project.getFinalDate().toString());
        responseDto.setId(project.getId().toString());

        return responseDto;
    }
}
