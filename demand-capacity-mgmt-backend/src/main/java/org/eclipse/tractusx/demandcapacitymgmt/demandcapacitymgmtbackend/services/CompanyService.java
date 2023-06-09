package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;

public interface CompanyService {
    CompanyEntity createCompany();

    CompanyEntity getCompanyById(Long id);
}
