package edu.josegc789.companyform.exception;

public enum ExceptionalMessages {
    INVALID_SESSION("Session is invalid"),
    EXTERNAL_ERROR("External Service Error");

    private final String message;

    ExceptionalMessages(String message) {
        this.message = message;
    }

    String getMessage(){
        return message;
    }

    public boolean equals(String str){
        return message.equals(str);
    }
}
