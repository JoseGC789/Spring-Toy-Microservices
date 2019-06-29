package edu.josegc789.companyform.exception;

public class RandomExternalError extends Exception{

    public RandomExternalError(ExceptionalMessages message) {
        super(message.getMessage());
    }
}
