package base;

public interface SessionServiceExt {

    // get session/new
    void retrieveSessionLoginForm();

    int getSessionLimit();

    void sessionLimit(boolean sessionLimiting);

    void setSessionLimit(int sessionLimit);

    int getSessionCount();

}
