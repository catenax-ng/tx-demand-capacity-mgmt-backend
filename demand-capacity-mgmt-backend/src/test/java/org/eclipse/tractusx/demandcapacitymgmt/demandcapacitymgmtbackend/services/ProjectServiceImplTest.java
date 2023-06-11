package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectRequestDto;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectRequestDto.TypeEnum;
import eclipse.tractusx.demand_capacity_mgmt_specification.model.ProjectResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.ProjectEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.enums.ProjectType;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.NotFoundException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.ProjectRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProject_shouldReturnGeneratedProjectResponseDto() {
        // Arrange
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setName("Test Project");
        projectRequestDto.setStartDate("2023-01-01");
        projectRequestDto.setEndDate("2023-12-31");
        projectRequestDto.setType(TypeEnum.DEMAND_CAPACITY);

        ProjectEntity savedProject = new ProjectEntity();
        savedProject.setId(1L);
        savedProject.setName("Test Project");
        savedProject.setInitialDate(LocalDateTime.parse("2023-01-01T00:00:00"));
        savedProject.setFinalDate(LocalDateTime.parse("2023-12-31T23:59:59"));
        savedProject.setType(ProjectType.DEMAND_CAPACITY);

        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(savedProject);

        // Act
        ProjectResponseDto result = projectService.createProject(projectRequestDto);

        // Assert
        verify(projectRepository, times(1)).save(any(ProjectEntity.class));
        Assertions.assertEquals(savedProject.getId().toString(), result.getId());
        Assertions.assertEquals(savedProject.getName(), result.getName());
        Assertions.assertEquals(savedProject.getInitialDate().toString(), result.getStartDate());
        Assertions.assertEquals(savedProject.getFinalDate().toString(), result.getEndDate());
    }

    @Test
    void getAllProjects_shouldReturnListOfProjectResponseDto() {
        // Arrange
        List<ProjectEntity> projectEntities = new ArrayList<>();
        projectEntities.add(
            new ProjectEntity(
                1L,
                "Project 1",
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-12-31T23:59:59"),
                ProjectType.DEMAND_CAPACITY
            )
        );
        projectEntities.add(
            new ProjectEntity(
                2L,
                "Project 2",
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-12-31T23:59:59"),
                ProjectType.DEMAND_CAPACITY
            )
        );

        when(projectRepository.findAll()).thenReturn(projectEntities);

        // Act
        List<ProjectResponseDto> result = projectService.getAllProjects();

        // Assert
        verify(projectRepository, times(1)).findAll();
        Assertions.assertEquals(projectEntities.size(), result.size());
        Assertions.assertEquals(projectEntities.get(0).getId().toString(), result.get(0).getId());
        Assertions.assertEquals(projectEntities.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(projectEntities.get(0).getInitialDate().toString(), result.get(0).getStartDate());
        Assertions.assertEquals(projectEntities.get(0).getFinalDate().toString(), result.get(0).getEndDate());
        Assertions.assertEquals(projectEntities.get(1).getId().toString(), result.get(1).getId());
        Assertions.assertEquals(projectEntities.get(1).getName(), result.get(1).getName());
        Assertions.assertEquals(projectEntities.get(1).getInitialDate().toString(), result.get(1).getStartDate());
        Assertions.assertEquals(projectEntities.get(1).getFinalDate().toString(), result.get(1).getEndDate());
    }

    @Test
    void getProjectById_shouldReturnProjectResponseDtoWhenProjectExists() {
        // Arrange
        ProjectEntity project = new ProjectEntity(
            1L,
            "Test Project",
            LocalDateTime.parse("2023-01-01T00:00:00"),
            LocalDateTime.parse("2023-12-31T23:59:59"),
            ProjectType.DEMAND_CAPACITY
        );
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Act
        ProjectResponseDto result = projectService.getProjectById(1l);

        // Assert
        verify(projectRepository, times(1)).findById(1L);
        Assertions.assertEquals(project.getId().toString(), result.getId());
        Assertions.assertEquals(project.getName(), result.getName());
        Assertions.assertEquals(project.getInitialDate().toString(), result.getStartDate());
        Assertions.assertEquals(project.getFinalDate().toString(), result.getEndDate());
    }

    @Test
    void getProjectById_shouldThrowNotFoundExceptionWhenProjectDoesNotExist() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> projectService.getProjectById(1l));
    }

    @Test
    void getProjectEntityById_shouldReturnProjectEntityWhenProjectExists() {
        // Arrange
        ProjectEntity project = new ProjectEntity(
            1L,
            "Test Project",
            LocalDateTime.parse("2023-01-01T00:00:00"),
            LocalDateTime.parse("2023-12-31T23:59:59"),
            ProjectType.DEMAND_CAPACITY
        );
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Act
        ProjectEntity result = projectService.getProjectEntityById(1l);

        // Assert
        verify(projectRepository, times(1)).findById(1L);
        Assertions.assertEquals(project, result);
    }

    @Test
    void getProjectEntityById_shouldThrowNotFoundExceptionWhenProjectDoesNotExist() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> projectService.getProjectEntityById(1l));
    }
}
