package edu.josegc789.companyform.controller;

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
public class TestContr{

    private final AsyncService asyncService;

    public TestContr(AsyncService asyncService){
        this.asyncService = asyncService;
    }

    @GetMapping("/future")
    public ResponseEntity<String> testFut() throws InterruptedException{
        ListenableFuture<String> a = asyncService.doAsync(String.valueOf("a"));
        Thread.sleep(100);
        a.cancel(true);
        return ResponseEntity.ok("a");
    }

    @GetMapping("/async")
    public ResponseEntity<String> testAsync() throws InterruptedException{
        StringBuilder builder = new StringBuilder();
        String elapsed = "";
        String endResult = "INTERRUPTED";
        LocalTime begin = LocalTime.now();
        List<ListenableFuture<String>> contentList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
             contentList.add(asyncService.doAsync(String.valueOf(i)));
        }

        List<CompletableFuture<String>> completableAsync = contentList.stream()
                .map(ListenableFuture::completable)
                .collect(Collectors.toList());

        CompletableFuture<List<String>> asyncTree = CompletableFuture.allOf(completableAsync.toArray(new CompletableFuture[0]))
                .thenApply(v -> completableAsync.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
        try{
            while(!asyncTree.isDone()){
                for(CompletableFuture<String> stringCompletableFuture : completableAsync){
                    if(stringCompletableFuture.isCompletedExceptionally()){
                        stringCompletableFuture.get();
                    }
                }
                Thread.sleep(100);
            }
            List<String> str = asyncTree.get();
            str.forEach(builder::append);
            endResult = builder.toString();

        } catch (Exception exception) {
            contentList.forEach(listenable -> listenable.cancel(true));
            log.error(String.valueOf(asyncTree.isCompletedExceptionally()));
        } finally{
            LocalTime end = LocalTime.now();
            elapsed = elapsed + MILLIS.between(begin, end) + "ms";
            log.info("Elapsed time: " + elapsed);
        }
        return ResponseEntity.ok(endResult + " --- " + elapsed + " --- " + new Random().nextInt(100));
    }
}
