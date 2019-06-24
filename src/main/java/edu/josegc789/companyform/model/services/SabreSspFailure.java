package edu.josegc789.companyform.model.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SabreSspFailure{
    private AtomicInteger sspCounter = new AtomicInteger(3);

    public String doTheFail(String num){
//        if(sspCounter.getAndIncrement() == 3){
//            sspCounter.getAndSet(0);
//            throw new RuntimeException("failed");
//        }
        if(new Random().nextInt(5) == 1){
            throw new RuntimeException("failed");
        }
        for(int i = 0; i < 100000000; i++){
            if(Thread.currentThread().isInterrupted()){
                throw new RuntimeException("interrupted");
            }
            log.debug(String.valueOf(i));
        }
        return num;
    }
}
