package edu.josegc789.companyform.model.services;

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

    private final ExternalFailingService ssp;
    private final UUID reqId = UUID.randomUUID();

    public AsyncService(ExternalFailingService ssp){
        this.ssp = ssp;
    }

    @Async
    public ListenableFuture<String> doAsync(String num) throws InterruptedException{
        log.info(Thread.currentThread().getName() + " began. --- " + reqId);
        String result;
        try{
            result = ssp.doTheFail(num);
        } catch(Exception ex){
            log.info(Thread.currentThread().getName() + " stopped because: " + ex.getMessage() + " --- " + reqId);
            throw ex;
        }
        log.info(Thread.currentThread().getName() + " ended. --- " + reqId);
        return new AsyncResult<>(result);
    }
}