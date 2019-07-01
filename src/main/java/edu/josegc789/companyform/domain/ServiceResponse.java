package edu.josegc789.companyform.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ServiceResponse {
    private static final Random ID = ThreadLocalRandom.current();
    private static final int BOUND = 1000;
    private final String value;
    private final String delay;
    private final int id;

    public static ServiceResponse from(String value, String delay){
        return new ServiceResponse(value, delay, ID.nextInt(BOUND));
    }
}
