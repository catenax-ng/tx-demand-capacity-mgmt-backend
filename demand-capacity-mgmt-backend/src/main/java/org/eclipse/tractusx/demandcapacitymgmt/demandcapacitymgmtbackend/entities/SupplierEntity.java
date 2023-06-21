package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUPPLIER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LEGAL_NAME")
    private String legalName;

    @Column(name = "NAME")
    private String edcUrl;
}
