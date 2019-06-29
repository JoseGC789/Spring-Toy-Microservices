package edu.josegc789.companyform.exception;

public final class RandomExternalError extends Exception{

    public RandomExternalError(ExceptionalMessages message) {
        super(message.getMessage());
    }
}
