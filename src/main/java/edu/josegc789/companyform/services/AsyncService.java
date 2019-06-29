package edu.josegc789.companyform.services;

import edu.josegc789.companyform.exception.AccessSessionException;
import edu.josegc789.companyform.exception.RandomExternalError;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class AsyncService{

    private final ExternalService ssp;
    private final UUID reqId = UUID.randomUUID();
    @Setter
    private CountDownLatch latch;
    @Setter
    private AccessSessionManager.AccessSession session;

    public AsyncService(ExternalService ssp){
        this.ssp = ssp;
    }

    @Async
    public Future<String> doAsync(String num) throws Exception {
        log.info(Thread.currentThread().getName() + " began. --- " + reqId);
        String result = "";
        try{
            result = ssp.doTheFail(num, session);
        } catch(AccessSessionException | InterruptedException ex){
            logStop(ex.getMessage());
            while(latch.getCount() > 0){
                latch.countDown();
            }
            throw ex;
        } catch (RandomExternalError ex){
            logStop(ex.getMessage());
        }finally{
            latch.countDown();
        }
        log.info(Thread.currentThread().getName() + " ended. --- " + reqId);
        return new AsyncResult<>(result);
    }

    private void logStop(String message) {
        log.error(Thread.currentThread().getName() + " stopped because: " + message + " --- " + reqId);
    }
}