package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemandCapacityMgmtBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemandCapacityMgmtBackendApplication.class, args);
    }
}
