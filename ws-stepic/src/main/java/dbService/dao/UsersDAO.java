package dbService.dao;

import base.UserProfile;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author v.chibrikov
 */
public class UsersDAO {

    private static final Logger logger = LogManager.getLogger(UsersDAO.class.getName());
    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet get(String login) throws SQLException {
        return executor.execQuery("select * from users where login='" + login + "'", result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public List<UsersDataSet> getAll() throws SQLException {
        List<UsersDataSet> usersDataSets = new ArrayList<>();
        return executor.execQuery("select * from users",
                result -> {
                    while ( result.next() ) {
                        usersDataSets.add(
                                new UsersDataSet(
                                        result.getLong(1),
                                        result.getString(2),
                                        result.getString(3) ) );
                    }
                    return usersDataSets;
                });
    }

    public long getUserId(String login) throws SQLException {
        return executor.execQuery("select id from users where login='" + login + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public int getUsersCount() throws SQLException {
        return executor.execQuery("select count(id) from users", result -> {
            result.next();
            return result.getInt(1);
        });
    }

    public void insertUser(UserProfile profile) throws SQLException {
        logger.info("db insert user");
        executor.execUpdate("insert into users (login, password) values ('" + profile.getLogin() + "','" + profile.getPassword() + "')");
    }

    public void removeUser(String login) throws SQLException {
        logger.info("db remove user");
        executor.execUpdate("DELETE FROM users WHERE login='" + login + "'");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(32) UNIQUE, password varchar(100), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }

    public void updateUser(String login, UserProfile user) throws SQLException {
        logger.info("db updating user");
        executor.execUpdate("UPDATE users SET login = '" + user.getLogin()
                + "', password = '" + user.getPassword()
                + "' WHERE id='" + getUserId(login)
                + "'");
    }

}
