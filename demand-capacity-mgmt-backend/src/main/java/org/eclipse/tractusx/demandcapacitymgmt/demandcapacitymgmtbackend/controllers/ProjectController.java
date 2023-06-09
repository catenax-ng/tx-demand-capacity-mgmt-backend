package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.ProjectApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectController implements ProjectApi {

    private final ProjectService projectService;

    @Override
    public ResponseEntity<ProjectResponseDto> getProjectById(String projectId) throws Exception {
        ProjectResponseDto responseDto = projectService.getProjectById();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Override
    public ResponseEntity<List<ProjectResponseDto>> getProjects() {
        List<ProjectResponseDto> responseDtos = projectService.getAllProjects();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @Override
    public ResponseEntity<ProjectResponseDto> postProject(ProjectRequestDto projectRequestDto) {
        ProjectResponseDto responseDto = projectService.createProject(projectRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
