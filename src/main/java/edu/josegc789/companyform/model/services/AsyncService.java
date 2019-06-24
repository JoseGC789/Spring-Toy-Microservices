package edu.josegc789.companyform.model.services;

import edu.josegc789.companyform.exception.CancelledRequestException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.WebApplicationContext;
import java.util.UUID;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class AsyncService{

    private final ExternalService ssp;
    private final UUID reqId = UUID.randomUUID();
    @Setter
    private AccessSessionManager.AccessSession session;

    public AsyncService(ExternalService ssp){
        this.ssp = ssp;
    }

    @Async
    public ListenableFuture<String> doAsync(String num) throws CancelledRequestException {
        log.info(Thread.currentThread().getName() + " began. --- " + reqId);
        String result;
        try{
            result = ssp.doTheFail(num, session);
        } catch (CancelledRequestException e){
            logStop(e.getMessage());
            throw e;
        }

        log.info(Thread.currentThread().getName() + " ended. --- " + reqId);
        return new AsyncResult<>(result);
    }

    private void logStop(String message) {
        log.error(Thread.currentThread().getName() + " stopped because: " + message + " --- " + reqId);
    }
}