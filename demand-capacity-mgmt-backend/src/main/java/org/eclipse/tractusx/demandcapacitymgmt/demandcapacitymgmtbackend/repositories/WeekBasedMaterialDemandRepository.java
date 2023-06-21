package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories;

import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.WeekBasedMaterialDemandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekBasedMaterialDemandRepository extends JpaRepository<WeekBasedMaterialDemandEntity, Integer> {

    List<WeekBasedMaterialDemandEntity> getAllByViewed(Boolean viewed);
}
