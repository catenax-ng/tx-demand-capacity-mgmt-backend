package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEMAND")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID")
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID")
    private CompanyEntity company;

    @Column(name = "REQUIRED_VALUE")
    private Double requiredValue;

    @Column(name = "DELIVERED_VALUE")
    private Double deliveredValue;

    @Column(name = "MAXIMUM_VALUE")
    private Double maximumValue;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DEMAND_CATEGORY")
    private String demandCategory;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "UNIT_MEASURE_ID", referencedColumnName = "ID")
    private UnitMeasureEntity unitMeasure;
}
