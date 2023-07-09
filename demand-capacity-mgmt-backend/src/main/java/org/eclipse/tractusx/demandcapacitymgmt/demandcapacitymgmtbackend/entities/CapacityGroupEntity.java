package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "capacity_group")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapacityGroupEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "id")
    private UUID id;

    @Column(name = "material_description_customer")
    private String materialDescriptionCustomer;

    @Column(name = "material_number_customer")
    private String materialNumberCustomer;

    @Column(name = "material_number_supplier")
    private String materialNumberSupplier;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "ID")
    private CompanyEntity customerId;

    @OneToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "ID")
    private CompanyEntity supplierId;

    @OneToOne
    @JoinColumn(name = "unity_of_measure_id", referencedColumnName = "ID")
    private UnitMeasureEntity unitMeasure;

    @OneToMany(mappedBy = "capacityGroupEntity", cascade = CascadeType.ALL)
    private List<CapacityTimeSeries> capacityTimeSeries;

    @OneToMany(mappedBy = "capacityGroupEntity", cascade = CascadeType.ALL)
    private List<LinkedDemandSeries> linkedDemandSeries;
}
