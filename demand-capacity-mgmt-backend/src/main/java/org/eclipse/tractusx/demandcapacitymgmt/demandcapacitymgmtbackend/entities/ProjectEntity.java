package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities.enums.ProjectType;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROJECT")
public class ProjectEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "INITIAL_DATE", nullable = false)
    private LocalDateTime initialDate;

    @Column(name = "FINAL_DATE")
    private LocalDateTime finalDate;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ProjectType type;
}
