package edu.josegc789.companyform.exception;

public class InterruptedTaskException extends CancelledRequestException {

    public InterruptedTaskException(ExceptionalMessages message) {
        super(message.getMessage());
    }
}
