package edu.josegc789.companyform.domain;

import edu.josegc789.companyform.exception.AccessSessionException;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import edu.josegc789.companyform.services.AccessSessionManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import java.util.concurrent.CountDownLatch;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExternalRequest{
    private final AccessSessionManager.AccessSession session;
    private final CountDownLatch latch;

    public static ExternalRequest from(int amount, AccessSessionManager.AccessSession session){
        return new ExternalRequest(session, new CountDownLatch(amount));
    }

    public void isUsable() throws AccessSessionException{
        if(session.isUnusable()){
            throw new AccessSessionException(ExceptionalMessages.INVALID_SESSION);
        }
    }

    public void invalidate(){
        while(latch.getCount() > 0){
            latch.countDown();
        }
    }

    public void countDown(){
        latch.countDown();
    }

    public void awaitCompletion() throws InterruptedException{
        latch.await();
    }
}