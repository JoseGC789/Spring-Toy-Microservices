package edu.josegc789.companyform.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ExceptionalMessages {
    INVALID_SESSION("Session is invalid"),
    INTERRUPTED_REQUEST("Request was interrupted"),
    EXTERNAL_ERROR("External Service Error");

    private static final String REGEX = " \\W?.*";
    private final String message;

    ExceptionalMessages(String message) {
        this.message = message;
    }

    String getMessage(){
        return message;
    }

    public static String of(Throwable exception){
        Matcher matcher = Pattern.compile(REGEX).matcher(exception.getMessage());
        if(matcher.find()){
            return matcher.group(0).trim();
        }
        return "";
    }
}
