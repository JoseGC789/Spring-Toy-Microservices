package edu.josegc789.companyform.exception;

public class RandomExternalError extends CancelledRequestException {

    public RandomExternalError(ExceptionalMessages message) {
        super(message.getMessage());
    }
}
