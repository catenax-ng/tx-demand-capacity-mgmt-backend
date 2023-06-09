package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import eclipse.tractusx.demand_capacity_mgmt_specification.model.Greeting;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GreetingServiceTest {

    @InjectMocks

    private Greeting greeting = createGreeting();

    private Greeting createGreeting() {
        Greeting greeting = new Greeting();
        greeting.setMessage("");
        return greeting;
    }
}
