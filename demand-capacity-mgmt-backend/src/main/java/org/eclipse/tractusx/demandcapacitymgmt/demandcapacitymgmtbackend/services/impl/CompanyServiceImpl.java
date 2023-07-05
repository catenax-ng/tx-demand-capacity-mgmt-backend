package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.impl;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.CompanyDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CompanyEntity;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions.BadRequestException;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories.CompanyRepository;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.CompanyService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyEntity createCompany() {
        return null;
    }

    @Override
    public CompanyEntity getCompanyById(UUID id) {
        Optional<CompanyEntity> company = companyRepository.findById(id);

        if (company.isEmpty()) {
            throw new BadRequestException("Company don't exist");
        }

        return company.get();
    }

    @Override
    public List<CompanyEntity> getCompanyIn(List<UUID> uuidList) {
        return companyRepository.findAllById(uuidList);
    }

    @Override
    public CompanyDto convertEntityToDto(CompanyEntity companyEntity) {
        CompanyDto companyDto = new CompanyDto();

        companyDto.setBpn(companyEntity.getBpn());
        companyDto.setMyCompany(companyEntity.getMyCompany());
        companyDto.setCompanyName(companyEntity.getCompanyName());
        companyDto.setCountry(companyEntity.getCountry());
        companyDto.setStreet(companyEntity.getStreet());
        companyDto.setNumber(companyEntity.getNumber());
        companyDto.setZipCode(companyEntity.getZipCode());

        return companyDto;
    }
}
