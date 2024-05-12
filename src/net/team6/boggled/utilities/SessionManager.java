package net.team6.boggled.utilities;

public class SessionManager {
    private static String sessionId;

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        SessionManager.sessionId = sessionId;
    }
}
