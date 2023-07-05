package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.entities;

import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unity_of_measure")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitMeasureEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "id")
    private UUID id;

    @Column(name = "code_value")
    private String codeValue;

    @Column(name = "display_value")
    private String displayValue;
}
