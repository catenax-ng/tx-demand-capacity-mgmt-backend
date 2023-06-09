package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectResponseDto;
import java.util.List;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.ProjectEntity;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectRequestDto projectRequestDto);

    void updateProject();

    List<ProjectResponseDto> getAllProjects();

    ProjectResponseDto getProjectById();

    ProjectEntity getProjectEntityById();
}
