package edu.josegc789.companyform.services;

import edu.josegc789.companyform.domain.ExternalRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
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
        List<Future<String>> listenableNum = submitTasks(amount, request);
        request.awaitCompletion();
        if(request.isExceptional()){
            sessionManager.closeSession(session);
            cancelTasks(listenableNum);
            builder = new StringBuilder(request.getExceptional().getMessage());
        }else{
            for(Future<String> num : listenableNum){
                builder.append(num.get());
            }
            sessionManager.releaseSession(session);
        }
        return builder.toString();
    }

    private void cancelTasks(List<Future<String>> listenableNum){
        listenableNum.forEach(listenable -> listenable.cancel(true));
    }

    private List<Future<String>> submitTasks(int amount, ExternalRequest payload) {
        List<Future<String>> listenableFutures = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            listenableFutures.add(asyncService.doAsync(String.valueOf(i), payload));
        }
        return listenableFutures;
    }
}
