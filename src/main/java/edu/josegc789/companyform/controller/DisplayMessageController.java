package edu.josegc789.companyform.controller;

import edu.josegc789.companyform.domain.ServiceResponse;
import edu.josegc789.companyform.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalTime;
import static edu.josegc789.companyform.exception.ExceptionalMessages.INVALID_SESSION;
import static java.time.temporal.ChronoUnit.MILLIS;

@Slf4j
@RestController
@CrossOrigin
public class DisplayMessageController {

    private static final int DEFAULT = 4;
    private final DocumentService documentService;

    public DisplayMessageController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/async/{amount}")
    public ResponseEntity<ServiceResponse> testAsync(@PathVariable(required = false) Integer amount) throws Exception{
        LocalTime begin = LocalTime.now();
        String elapsed = "";
        String endResult = documentService.acquireDocument(Math.abs(amount));
        elapsed = elapsed + MILLIS.between(begin,  LocalTime.now()) + "ms";
        log.info("Elapsed time: " + elapsed);
        return buildResponse(amount, elapsed, endResult);
    }

    private ResponseEntity<ServiceResponse> buildResponse(@PathVariable(required = false) Integer amount, String elapsed, String endResult) {
        if(amount.equals(endResult.length())){
            return ResponseEntity.ok(ServiceResponse.from(endResult, elapsed));
        } else {
            if(INVALID_SESSION.equals(endResult)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ServiceResponse.from(endResult, elapsed));
            }
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(ServiceResponse.from(endResult, elapsed));
        }
    }

    @GetMapping({"/async/0","/async"})
    public ResponseEntity<ServiceResponse> testAsync() throws Exception{
        return testAsync(DEFAULT);
    }
}
