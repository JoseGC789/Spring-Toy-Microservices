package edu.josegc789.companyform.services;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class AccessSessionManager {

    public AccessSession acquireSession(){
        return AccessSession.acquire();
    }

    public void releaseSession(AccessSession session){
        session.finish();
    }

    public void closeSession(AccessSession session){
        session.finish();
    }

    public final static class AccessSession{
        private static final Random FAILURE_CHANCE = new Random();
        private static final int UNUSABLE = 0;
        private static final int BOUND = 10;
        private int usable;

        private AccessSession(int usable) {
            this.usable = usable;
        }

        private static AccessSession acquire(){
            return new AccessSession(FAILURE_CHANCE.nextInt(BOUND));
        }

        public boolean isUnusable(){
            return usable == UNUSABLE;
        }

        private void finish(){
            usable = UNUSABLE;
        }
    }
}
