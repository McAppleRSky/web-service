package base;

import java.util.List;

/**
 * @author v.chibrikov
 */
public interface AccountService {

    // post user
    void createUser(String login, String password);

    // get user
    List<UserProfile> getUserAll();

    // get user/id
    UserProfile getUser(String login);

    // patch user/id
    void updateUser(String login, String login1, String password);

    // delete user/id
    void deleteUser(String login);

    // post session
    boolean addSession(String sessionId, String login);

    // delete session
    void deleteSession(String sessionId);

    UserProfile getUserByLogin(String login);

    String getUserBySessionId(String sessionId);

    int getUsersCount();

    int getUsersLimit();

    void setUsersLimit(int usersLimit);
}
