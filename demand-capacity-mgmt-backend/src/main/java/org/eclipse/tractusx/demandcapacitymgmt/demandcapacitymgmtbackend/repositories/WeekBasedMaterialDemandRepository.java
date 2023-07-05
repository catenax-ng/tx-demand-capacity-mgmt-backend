package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories;

import java.util.List;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.WeekBasedMaterialDemandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekBasedMaterialDemandRepository extends JpaRepository<WeekBasedMaterialDemandEntity, Integer> {
    List<WeekBasedMaterialDemandEntity> getAllByViewed(Boolean viewed);
}
