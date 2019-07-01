package edu.josegc789.companyform.services;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum FailGenerator{
    SESSION(6),
    EXTERNAL(4);

    private static final Random RANDOM = ThreadLocalRandom.current();
    private final int bound;

    FailGenerator(int bound){
        this.bound = bound;
    }

    public boolean hasFailed(){
        return RANDOM.nextInt(bound) == 0;
    }
}
