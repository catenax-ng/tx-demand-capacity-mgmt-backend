package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.converters.WeekBasedCapacityGroupConverter;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.converters.WeekBasedMaterialConverter;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedCapacityGroup;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.jsonEntities.WeekBasedMaterialDemand;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "week_based_material_demand")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekBasedCapacityGroupEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Convert(converter = WeekBasedCapacityGroupConverter.class)
    @Column(name = "data", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private WeekBasedCapacityGroup weekBasedCapacityGroup;

    @Column(name = "viewed")
    private Boolean viewed;
}
