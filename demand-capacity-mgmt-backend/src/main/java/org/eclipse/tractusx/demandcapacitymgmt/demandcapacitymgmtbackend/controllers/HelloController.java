package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.controllers;

import lombok.AllArgsConstructor;
import org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.services.GreetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
<<<<<<< HEAD
public class HelloController {}
=======
public class HelloController implements DefaultApi {

    private final GreetingService greetingService;

    @Override
    public ResponseEntity<Greeting> getGreeting() {
        greetingService.saveGreeting(null);
        return null;
    }
}
>>>>>>> feat/DCMFOSS-16
