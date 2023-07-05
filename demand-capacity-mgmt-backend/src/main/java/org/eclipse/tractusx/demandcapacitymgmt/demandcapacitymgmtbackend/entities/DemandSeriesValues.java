package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "demand_series_values")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemandSeriesValues {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "demand_series_id")
    private DemandSeries demandSeries;

    @Column(name = "calendar_week", nullable = false)
    private LocalDateTime calendarWeek;

    @Column(name = "demand", nullable = false)
    private Double demand;
}
