package edu.josegc789.companyform.controller;

import edu.josegc789.companyform.exception.CancelledRequestException;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import edu.josegc789.companyform.model.services.AccessSessionManager;
import edu.josegc789.companyform.model.services.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoUnit.MILLIS;

@Slf4j
@RestController
public class DisplayMessageController {

    private final AsyncService asyncService;
    private final AccessSessionManager sessionManager;

    public DisplayMessageController(AsyncService asyncService, AccessSessionManager sessionManager) {
        this.asyncService = asyncService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/async")
    public ResponseEntity<String> testAsync() {
        AccessSessionManager.AccessSession session = sessionManager.acquireSession();
        asyncService.setSession(session);
        StringBuilder builder = new StringBuilder();
        String elapsed = "";
        String endResult;
        LocalTime begin = LocalTime.now();

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
            LocalTime end = LocalTime.now();
            elapsed = elapsed + MILLIS.between(begin, end) + "ms";
            log.info("Elapsed time: " + elapsed);
        }
        return ResponseEntity.ok(endResult + " --- " + elapsed + " --- " + new Random().nextInt(100));
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
