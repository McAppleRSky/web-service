package base;

import dbService.DBException;

import java.util.List;

/**
 * @author v.chibrikov
 */
public interface DBService {

    void createUsersTable() throws DBException;

    void removeUsersTable() throws DBException;

    long addUser(UserProfile userProfile) throws DBException;

    void removeUser(String login) throws DBException;

    UserProfile getUser(String login) throws DBException;

    void check() throws DBException;

    List<UserProfile> findAllUsers();

    long update(String login, UserProfile userProfile);

}
