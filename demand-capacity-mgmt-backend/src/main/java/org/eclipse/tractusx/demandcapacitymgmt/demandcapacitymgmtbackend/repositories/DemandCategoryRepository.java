package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories;

import java.util.UUID;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandCategoryRepository extends JpaRepository<DemandCategoryEntity, UUID> {}
