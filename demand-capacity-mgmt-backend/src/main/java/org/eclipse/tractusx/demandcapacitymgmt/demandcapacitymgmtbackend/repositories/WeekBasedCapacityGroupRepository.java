package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.repositories;

import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.WeekBasedCapacityGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeekBasedCapacityGroupRepository extends JpaRepository<WeekBasedCapacityGroupEntity, Integer> {

    List<WeekBasedCapacityGroupEntity> getAllByViewed(Boolean viewed);

}
