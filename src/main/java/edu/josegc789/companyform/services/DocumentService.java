package edu.josegc789.companyform.services;

import edu.josegc789.companyform.domain.ExternalRequest;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final AsyncSubmitterService asyncService;
    private final AccessSessionManager sessionManager;

    public DocumentService(AsyncSubmitterService asyncService, AccessSessionManager sessionManager) {
        this.asyncService = asyncService;
        this.sessionManager = sessionManager;
    }

    public String acquireDocument(int amount) throws Exception {
        StringBuilder builder = new StringBuilder();
        AccessSessionManager.AccessSession session = sessionManager.acquireSession();
        ExternalRequest request = ExternalRequest.from(amount, session);
        List<CompletableFuture<String>> listenableNum = submitTasks(amount, request);
        request.awaitCompletion();
        try{
            for(Future<String> num : exceptional(listenableNum)){
                num.get();
            }
            for(Future<String> num : listenableNum){
                builder.append(num.get());
            }
            sessionManager.releaseSession(session);
        } catch (ExecutionException | InterruptedException exception){
            sessionManager.closeSession(session);
            cancelTasks(listenableNum);
            builder = new StringBuilder(ExceptionalMessages.of(exception));
        }
        return builder.toString();
    }

    private List<Future<String>> exceptional(List<CompletableFuture<String>> listenableNum) {
        return listenableNum.stream()
                .filter(CompletableFuture::isCompletedExceptionally)
                .collect(Collectors.toList());
    }

    private void cancelTasks(List<CompletableFuture<String>> listenableNum){
        listenableNum.forEach(listenable -> listenable.cancel(true));
    }

    private List<CompletableFuture<String>> submitTasks(int amount, ExternalRequest payload) throws Exception{
        List<CompletableFuture<String>> listenableFutures = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            listenableFutures.add(asyncService.doAsync(String.valueOf(i), payload));
        }
        return listenableFutures;
    }
}
