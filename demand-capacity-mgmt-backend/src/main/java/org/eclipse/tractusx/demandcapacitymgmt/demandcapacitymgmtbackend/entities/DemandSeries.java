package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "demand_series")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandSeries {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "id")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "customer_location_id", referencedColumnName = "ID")
    private CompanyEntity customerLocation;

    @OneToOne
    @JoinColumn(name = "expected_supplier_location_id", referencedColumnName = "ID")
    private CompanyEntity expectedSupplierLocation;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "material_demand_id", referencedColumnName = "ID")
    private MaterialDemandEntity materialDemandEntity;

    @OneToOne
    @JoinColumn(name = "demand_category_code_id", referencedColumnName = "ID")
    private DemandCategoryEntity demandCategory;

    @OneToMany(mappedBy = "demandSeries", cascade = { CascadeType.ALL })
    private List<DemandSeriesValues> demandSeriesValues;
}
