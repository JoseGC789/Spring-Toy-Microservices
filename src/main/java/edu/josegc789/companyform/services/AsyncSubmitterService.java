package edu.josegc789.companyform.services;

import edu.josegc789.companyform.domain.ExternalRequest;
import edu.josegc789.companyform.exception.AccessSessionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class AsyncSubmitterService{

    private final ExternalService ssp;
    private final UUID reqId = UUID.randomUUID();

    public AsyncSubmitterService(ExternalService ssp){
        this.ssp = ssp;
    }

    @Async
    public CompletableFuture<String> doAsync(String num, ExternalRequest payload) throws Exception {
        log.info(Thread.currentThread().getName() + " began. --- " + reqId);
        String result = "";
        try{
            result = ssp.doTheFail(num, payload);
        } catch(AccessSessionException | InterruptedException ex){
            logStop(ex.getMessage());
            payload.invalidate();
            throw ex;
        } catch (Exception ex){
            logStop(ex.getMessage());
        }finally{
            payload.countDown();
        }
        log.info(Thread.currentThread().getName() + " ended. --- " + reqId);
        return CompletableFuture.completedFuture(result);
    }

    private void logStop(String message) {
        log.error(Thread.currentThread().getName() + " stopped because: " + message + " --- " + reqId);
    }
}