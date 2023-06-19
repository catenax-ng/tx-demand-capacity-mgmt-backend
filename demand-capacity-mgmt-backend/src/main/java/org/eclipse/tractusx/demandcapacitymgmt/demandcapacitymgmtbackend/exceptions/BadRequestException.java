package org.eclipse.tractusx.demandcapacitymgmt.demandcapacitymgmtbackend.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
