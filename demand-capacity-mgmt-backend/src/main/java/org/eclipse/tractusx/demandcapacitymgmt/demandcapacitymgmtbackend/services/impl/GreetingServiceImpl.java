package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.Greeting;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.ProjectEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.SupplierEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.UnitMeasureEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.DemandRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.ProjectRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.SupplierRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.UnitMeasureRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.GreetingService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class GreetingServiceImpl implements GreetingService {

    
    private final ProjectRepository projectRepository;
    private final SupplierRepository supplierRepository;
    private final DemandRepository demandRepository;
    private final UnitMeasureRepository unitMeasureRepository;

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }


    @Override
    public void saveGreeting(Greeting greeting) {

        // Define the input for creating a project
        ProjectEntity project = ProjectEntity.builder()
                .id(1)
                .name("Project 1")
                .initial_date(LocalDateTime.now())
                .build();
        projectRepository.save(project);

        // Define the input for creating a supplier
        SupplierEntity supplier = SupplierEntity.builder()
                .id(1)
                .name("Supplier 1")
                .build();
        supplierRepository.save(supplier);


        // Define the input for creating a unitMeasure
        UnitMeasureEntity unitMeasure = UnitMeasureEntity.builder()
                .id(1)
                .un("GRM")
                .name("gram")
                .build();
        unitMeasureRepository.save(unitMeasure);

        // Define the input for creating a demand
        DemandEntity demand = DemandEntity.builder()
                .id(1)
                .project(project)
                .supplier(supplier)
                .requiredCapacity(2.0)
                .actualDemand(4.0)
                .description("description of demand")
                .demandCategory("Category of the Demand")
                .unitMeasure(unitMeasure)
                .build();
        demandRepository.save(demand);
        
    }
      

    @Override
    public void findById(String id) {}
}
