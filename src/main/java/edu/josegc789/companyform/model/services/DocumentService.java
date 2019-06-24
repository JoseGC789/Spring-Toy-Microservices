package edu.josegc789.companyform.model.services;

import edu.josegc789.companyform.exception.CancelledRequestException;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentService {

    private final AsyncService asyncService;
    private final AccessSessionManager sessionManager;

    public DocumentService(AsyncService asyncService, AccessSessionManager sessionManager) {
        this.asyncService = asyncService;
        this.sessionManager = sessionManager;
    }

    public String acquireDocument() {
        AccessSessionManager.AccessSession session = sessionManager.acquireSession();
        asyncService.setSession(session);
        StringBuilder builder = new StringBuilder();
        String endResult;

        List<ListenableFuture<String>> listenableFutures = getListenableFutures(5);

        List<CompletableFuture<String>> completableFutures = listenableFutures.stream()
                .map(ListenableFuture::completable)
                .collect(Collectors.toList());

        CompletableFuture<List<String>> completableTree = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> completableFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
        try{
            while(!completableTree.isDone()){
                for(CompletableFuture<String> stringCompletableFuture : completableFutures){
                    if(stringCompletableFuture.isCompletedExceptionally()){
                        stringCompletableFuture.get();
                    }
                }
                Thread.sleep(50);
            }
            List<String> str = completableTree.get();
            str.forEach(builder::append);
            endResult = builder.toString();

        } catch (Exception exception) {
            endResult = ExceptionalMessages.of(exception);
            listenableFutures.forEach(listenable -> listenable.cancel(true));
        } finally{
            sessionManager.releaseSession(session);
        }
        return endResult;
    }

    private List<ListenableFuture<String>> getListenableFutures(int amount) {
        List<ListenableFuture<String>> contentList = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            try{
                contentList.add(asyncService.doAsync(String.valueOf(i)));
            }catch(CancelledRequestException e){
                e.printStackTrace();
            }
        }
        return contentList;
    }
}
