package edu.josegc789.companyform.domain;

import edu.josegc789.companyform.exception.AccessSessionException;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import edu.josegc789.companyform.services.AccessSessionManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExternalRequest{
    private final AccessSessionManager.AccessSession session;
    private final CountDownLatch latch;
    private final AtomicReference<Exception> exceptional;

    public static ExternalRequest from(int amount, AccessSessionManager.AccessSession session){
        return new ExternalRequest(session, new CountDownLatch(amount), new AtomicReference<>(null));
    }

    public Exception getExceptional(){
        return exceptional.get();
    }

    public boolean isExceptional(){
        return exceptional.get() != null;
    }

    public void isUnusable() throws AccessSessionException, InterruptedException {
        Thread.sleep(300);
        if(session.isUnusable()){
            throw new AccessSessionException(ExceptionalMessages.INVALID_SESSION);
        }
    }

    public void invalidate(Exception ex){
        exceptional.compareAndSet(null, ex);
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