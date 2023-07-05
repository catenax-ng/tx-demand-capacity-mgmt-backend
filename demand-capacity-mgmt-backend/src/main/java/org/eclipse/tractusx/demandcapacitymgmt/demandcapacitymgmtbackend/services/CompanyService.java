package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import java.util.UUID;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;

public interface CompanyService {
    CompanyEntity createCompany();

    CompanyEntity getCompanyById(UUID id);
}
