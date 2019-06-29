package edu.josegc789.companyform.exception;

public final class AccessSessionException extends Exception{

    public AccessSessionException(ExceptionalMessages message) {
        super(message.getMessage());
    }
}
