package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories;

import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.DemandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<DemandEntity, Integer> {}
