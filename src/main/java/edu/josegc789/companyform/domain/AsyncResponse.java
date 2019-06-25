package edu.josegc789.companyform.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import java.util.Random;

@Getter
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AsyncResponse {
    private static final Random ID = new Random();
    private static final int BOUND = 100;
    private String value;
    private String delay;
    private int id;

    public static AsyncResponse of(String value, String delay){
        return new AsyncResponse(value, delay, ID.nextInt(BOUND));
    }
}
