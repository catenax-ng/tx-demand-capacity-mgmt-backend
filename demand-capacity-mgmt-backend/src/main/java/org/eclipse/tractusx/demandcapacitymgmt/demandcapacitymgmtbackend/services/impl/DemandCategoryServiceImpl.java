package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.DemandCategoryResponse;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandCategoryEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.DemandCategoryRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.DemandCategoryService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DemandCategoryServiceImpl implements DemandCategoryService {

    private final DemandCategoryRepository demandCategoryRepository;

    @Override
    public DemandCategoryEntity findById(UUID id) {
        Optional<DemandCategoryEntity> demandCategory = demandCategoryRepository.findById(id);

        if (demandCategory.isEmpty()) {
            throw new BadRequestException("not a valid ID");
        }

        return demandCategory.get();
    }

    @Override
    public DemandCategoryResponse convertEntityToDto(DemandCategoryEntity demandCategory) {
        DemandCategoryResponse demandCategoryResponse = new DemandCategoryResponse();
        demandCategoryResponse.setId(demandCategory.getId().toString());
        demandCategoryResponse.setDemandCategoryCode(demandCategory.getDemandCategoryCode());
        demandCategoryResponse.setDemandCategoryName(demandCategory.getDemandCategoryName());

        return demandCategoryResponse;
    }
}
