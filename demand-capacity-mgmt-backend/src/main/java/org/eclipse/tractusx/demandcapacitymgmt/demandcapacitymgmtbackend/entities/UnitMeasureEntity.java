package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "UNIT_MEASURE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitMeasureEntity {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "UN")
    private String un;

    @Column(name = "NAME")
    private String name;

    // Getters and setters are automatically generated by Lombok

}
