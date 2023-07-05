package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import java.util.UUID;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.UnitMeasureEntity;

public interface UnityOfMeasureService {
    UnitMeasureEntity findById(UUID id);
}
