package edu.josegc789.companyform.exception;

public class AccessSessionException extends Exception{

    public AccessSessionException(ExceptionalMessages message) {
        super(message.getMessage());
    }
}
