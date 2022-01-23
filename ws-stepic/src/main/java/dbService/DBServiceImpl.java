package dbService;

import base.DBService;
import base.UserProfile;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author v.chibrikov
 */
public class DBServiceImpl implements DBService {
    private final Connection connection;

    public DBServiceImpl() {
        this.connection = getH2Connection();
    }

    @Override
    public void createUsersTable() throws DBException {
        try {
            (new UsersDAO(connection)).createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void removeUsersTable() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public long addUser(UserProfile userProfile) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.insertUser(userProfile);
            connection.commit();
            return dao.getUserId(userProfile.getLogin());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public void removeUser(String login) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.removeUser(login);
            connection.commit();
            //return dao.getUserId(userProfile.getLogin());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public UserProfile getUser(String login) throws DBException {
        try {
            UsersDAO dao = new UsersDAO(connection);
            UsersDataSet dataSet = dao.get(login);
            return new UserProfile(dataSet.getLogin(), dataSet.getPassword());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void check() throws DBException {
        try {
            UsersDAO dao = new UsersDAO(connection);
            int count = dao.getUsersCount();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<UserProfile> findAllUsers() {
        List<UserProfile> result = null;
        UsersDAO dao = new UsersDAO(connection);
        try {
            result = new ArrayList();
            for (UsersDataSet usersDataSet : dao.getAll()) {
                result.add(new UserProfile(
                        usersDataSet.getLogin(),
                        usersDataSet.getPassword()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public long update(String login, UserProfile userProfile) {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.updateUser(login, userProfile);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    private static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "test";
            String pass = "test";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
