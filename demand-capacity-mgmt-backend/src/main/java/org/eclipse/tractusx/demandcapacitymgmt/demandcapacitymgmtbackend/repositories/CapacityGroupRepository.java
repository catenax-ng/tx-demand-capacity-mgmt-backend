package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories;

import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.CapacityGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CapacityGroupRepository extends JpaRepository<CapacityGroupEntity, UUID> {
}
