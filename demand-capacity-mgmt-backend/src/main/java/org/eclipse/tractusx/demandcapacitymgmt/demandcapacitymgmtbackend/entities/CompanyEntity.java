package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COMPANY")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;
}
