package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIT_MEASURE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitMeasureEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "UN")
    private String un;

    @Column(name = "NAME")
    private String name;
}
