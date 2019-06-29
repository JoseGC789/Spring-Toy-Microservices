package edu.josegc789.companyform.services;

import edu.josegc789.companyform.exception.ExceptionalMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
@Slf4j
public class DocumentService {

    private final AsyncService asyncService;
    private final AccessSessionManager sessionManager;

    public DocumentService(AsyncService asyncService, AccessSessionManager sessionManager) {
        this.asyncService = asyncService;
        this.sessionManager = sessionManager;
    }

    public String acquireDocument(int amount) throws Exception {
        StringBuilder builder = new StringBuilder();
        final CountDownLatch latch = new CountDownLatch(amount);
        AccessSessionManager.AccessSession session = sessionManager.acquireSession();
        asyncService.setLatch(latch);
        asyncService.setSession(session);
        List<Future<String>> listenableFutures = callAsyncService(amount);
        latch.await();
        try{
            for(Future<String> future : listenableFutures){
                builder.append(future.get());
            }
            sessionManager.releaseSession(session);
        } catch (ExecutionException | InterruptedException exception){
            sessionManager.closeSession(session);
            listenableFutures.forEach(listenable -> listenable.cancel(true));
            builder = new StringBuilder(ExceptionalMessages.of(exception));
        }
        return builder.toString();
    }

    private List<Future<String>> callAsyncService(int amount) throws Exception{
        List<Future<String>> contentList = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            contentList.add(asyncService.doAsync(String.valueOf(i)));
        }
        return contentList;
    }
}
