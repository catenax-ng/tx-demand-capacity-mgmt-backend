package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "linked_demand_series")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkedDemandSeries {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "demand_category_code_id", referencedColumnName = "ID")
    private DemandCategoryEntity demandCategory;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "ID")
    private CompanyEntity customerId;

    @Column(name = "material_number_customer")
    private String materialNumberCustomer;

    @Column(name = "material_number_supplier")
    private String materialNumberSupplier;

    @ManyToOne
    @JoinColumn(name = "capacity_group_id")
    private CapacityGroupEntity capacityGroupEntity;
}
