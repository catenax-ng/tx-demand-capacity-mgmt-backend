package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import eclipse.tractusx.demand_capacity_mgmt_specification.api.ProjectApi;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProjectController implements ProjectApi {
    @Override
    public ResponseEntity<ProjectResponseDto> getProjectById(String projectId) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProjectResponseDto>> getProjects() {
        return null;
    }

    @Override
    public ResponseEntity<ProjectResponseDto> postProject(ProjectRequestDto projectRequestDto) {
        return null;
    }
}
