package edu.josegc789.companyform.services;

import org.springframework.stereotype.Service;
import static edu.josegc789.companyform.services.FailGenerator.SESSION;

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
        private boolean unusable;

        private AccessSession(boolean unusable) {
            this.unusable = unusable;
        }

        private static AccessSession acquire(){
            return new AccessSession(SESSION.hasFailed());
        }

        public boolean isUnusable(){
            return unusable;
        }

        private void finish(){
            unusable = false;
        }
    }
}
