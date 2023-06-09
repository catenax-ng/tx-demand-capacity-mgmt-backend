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
<<<<<<< HEAD
=======
    private GreetingService greetingService;
>>>>>>> 050dcf83bddafde6c6c1025d6d7b5d2ca6eb2851

    private Greeting greeting = createGreeting();

    private Greeting createGreeting() {
        Greeting greeting = new Greeting();
        greeting.setMessage("");
        return greeting;
    }
}
