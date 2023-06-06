package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories;

import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {}
