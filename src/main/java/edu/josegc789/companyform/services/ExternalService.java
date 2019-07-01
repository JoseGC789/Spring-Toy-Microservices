package edu.josegc789.companyform.services;

import edu.josegc789.companyform.domain.ExternalRequest;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import edu.josegc789.companyform.exception.RandomExternalError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static edu.josegc789.companyform.services.FailGenerator.EXTERNAL;

@Service
@Slf4j
public class ExternalService {

    public String doTheFail(String num, ExternalRequest payload) throws Exception {
        payload.isUnusable();
        failRandomly();
        Thread.sleep(300);
        return num;
    }

    private void failRandomly() throws RandomExternalError{
        if(EXTERNAL.hasFailed()){
            throw new RandomExternalError(ExceptionalMessages.EXTERNAL_ERROR);
        }
    }
}
