package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "capacity_time_series")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CapacityTimeSeries {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "id")
    private UUID id;

    @Column(name = "calendar_week", nullable = false)
    private LocalDateTime calendarWeek;

    @Column(name = "actual_capacity", nullable = false)
    private Double actualCapacity;

    @Column(name = "maximum_capacity", nullable = false)
    private Double maximumCapacity;

    @ManyToOne
    @JoinColumn(name = "capacity_group_id")
    private CapacityGroupEntity capacityGroupEntity;
}
