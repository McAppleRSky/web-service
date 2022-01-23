package frontend;

import base.*;
import dbService.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author v.chibrikov
 */
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class.getName());
    private final Map <String, UserProfile> loginToProfile;
    private final Map <String ,String//UserProfile
            > sessionIdToProfile;
    private final DBService dbService;
    private int usersCount;
    private int usersLimit;
    private AtomicInteger usersCreating = new AtomicInteger(0);

    public AccountServiceImpl(DBService dbService, int usersLimit) {
        this.dbService = dbService;
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        this.usersLimit = usersLimit;
    }

    @Override
    public void createUser(String login, String password) {
        try {
            long result = dbService.addUser(new UserProfile(login, password));
        } catch (DBException e) {
            logger.error("Can't sign up: " + e.getMessage());
        }
    }

    @Override
    public UserProfile getUserByLogin(String login) {
        try {
            return dbService.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserBySessionId(String sessionId) {
        return null;
    }

    @Override
    public List<UserProfile> getUserAll() {
        return dbService.findAllUsers();
    }

    @Override
    public UserProfile getUser(String login) {
        UserProfile userProfile = null;
        try {
            userProfile = dbService.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
            logger.error("DBException from getUser");
        }
        return userProfile;
    }

    @Override
    public void updateUser(String login, String login1, String password) {
        UserProfile user = null;
        try {
            user = dbService.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (user != null) {
            dbService.update(user.getLogin(), new UserProfile(login1, password));
        }
    }

    @Override
    public void deleteUser(String login) {
        UserProfile user = null;
        try {
            user = dbService.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (user != null) {
            try {
                dbService.removeUser(login);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean addSession(String sessionId, String login) {
        usersCount++;
        if ( sessionIdToProfile.containsKey(sessionId) ) {
            return false;
        } else {
            sessionIdToProfile.put(sessionId, login);
            return true;
        }
    }

    @Override
    public void deleteSession(String sessionId) {
        usersCount--;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getUsersLimit() {
        return usersLimit;
    }

    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }

}
