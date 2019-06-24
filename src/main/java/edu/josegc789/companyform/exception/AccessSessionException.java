package edu.josegc789.companyform.exception;

public class AccessSessionException extends CancelledRequestException {

    public AccessSessionException(ExceptionalMessages message) {
        super(message.getMessage());
    }
}
