package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.CompanyDto;
import java.util.List;
import java.util.UUID;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;

public interface CompanyService {
    CompanyEntity createCompany();

    CompanyEntity getCompanyById(UUID id);

    List<CompanyEntity> getCompanyIn(List<UUID> uuidList);

    CompanyDto convertEntityToDto(CompanyEntity companyEntity);
}
