package edu.josegc789.companyform.services;

import edu.josegc789.companyform.exception.AccessSessionException;
import edu.josegc789.companyform.exception.ExceptionalMessages;
import edu.josegc789.companyform.exception.RandomExternalError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class ExternalService {
    private static final Random RANDOM = ThreadLocalRandom.current();
    private static final int BOUND = 10;

    public String doTheFail(String num, AccessSessionManager.AccessSession session) throws Exception {
        checkSession(session);
        failRandomly();
        Thread.sleep(500);
        return num;
    }

    private void checkSession(AccessSessionManager.AccessSession session) throws AccessSessionException{
        if(session.isUnusable()){
            throw new AccessSessionException(ExceptionalMessages.INVALID_SESSION);
        }
    }

    private void failRandomly() throws RandomExternalError{
        if(RANDOM.nextInt(BOUND) == 0){
            throw new RandomExternalError(ExceptionalMessages.EXTERNAL_ERROR);
        }
    }
}
