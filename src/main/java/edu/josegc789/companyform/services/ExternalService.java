package edu.josegc789.companyform.services;

import edu.josegc789.companyform.exception.AccessSessionException;
import edu.josegc789.companyform.exception.CancelledRequestException;
import edu.josegc789.companyform.exception.InterruptedTaskException;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import edu.josegc789.companyform.exception.RandomExternalError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class ExternalService {
    private AtomicInteger sspCounter = new AtomicInteger(3);

    public String doTheFail(String num, AccessSessionManager.AccessSession session) throws CancelledRequestException {
//        if(sspCounter.getAndIncrement() == 3){
//            sspCounter.getAndSet(0);
//            throw new RuntimeException("failed");
//        }
        checkSession(session);
        failRandomly(10);
        for(int i = 0; i < 100000000; i++){
            checkInterruption();
            log.debug(String.valueOf(i));
        }
        return num;
    }

    private void checkInterruption() throws CancelledRequestException {
        if(Thread.currentThread().isInterrupted()){
            throw new InterruptedTaskException(ExceptionalMessages.INTERRUPTED_REQUEST);
        }
    }

    private void checkSession(AccessSessionManager.AccessSession session) throws CancelledRequestException {
        if(session.isUnusable()){
            throw new AccessSessionException(ExceptionalMessages.INVALID_SESSION);
        }
    }

    private void failRandomly(int bound) throws CancelledRequestException {
        if(new Random().nextInt(bound) == 0){
            throw new RandomExternalError(ExceptionalMessages.EXTERNAL_ERROR);
        }
    }
}
