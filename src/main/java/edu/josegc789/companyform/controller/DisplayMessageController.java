package edu.josegc789.companyform.controller;

import edu.josegc789.companyform.domain.AsyncResponse;
import edu.josegc789.companyform.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MILLIS;

@Slf4j
@RestController
public class DisplayMessageController {

    private final DocumentService documentService;

    public DisplayMessageController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/async")
    public ResponseEntity<AsyncResponse> testAsync() {
        LocalTime begin = LocalTime.now();
        String elapsed = "";
        String endResult = documentService.acquireDocument();
        elapsed = elapsed + MILLIS.between(begin,  LocalTime.now()) + "ms";
        log.info("Elapsed time: " + elapsed);
        return ResponseEntity.ok(AsyncResponse.of(endResult, elapsed));
    }
}
