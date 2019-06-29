package edu.josegc789.companyform.controller;

import edu.josegc789.companyform.domain.AsyncResponse;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import edu.josegc789.companyform.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;

@Slf4j
@RestController
public class DisplayMessageController {

    private static final int DEFAULT = 4;
    private final DocumentService documentService;

    public DisplayMessageController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/async/{amount}")
    public ResponseEntity<AsyncResponse> testAsync(@PathVariable(required = false) Integer amount) throws Exception{
        LocalTime begin = LocalTime.now();
        String elapsed = "";
        String endResult = documentService.acquireDocument(Math.abs(amount));
        elapsed = elapsed + MILLIS.between(begin,  LocalTime.now()) + "ms";
        log.info("Elapsed time: " + elapsed);

        if(amount.equals(endResult.length())){
            return ResponseEntity.ok(AsyncResponse.of(endResult, elapsed));
        } else {
            if(ExceptionalMessages.INVALID_SESSION.getMessage().equals(endResult)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(AsyncResponse.of(ExceptionalMessages.INVALID_SESSION.getMessage(), elapsed));
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(AsyncResponse.of(endResult, elapsed));
        }
    }

    @GetMapping({"/async/0","/async"})
    public ResponseEntity<AsyncResponse> testAsync() throws Exception{
        return testAsync(DEFAULT);
    }
}
