package base;

public interface UserServiceExt {

    // get user/new
    void retrieveUserCreateForm();

    // patch user
    void updateUserBySession();

    // delete user
    void deleteUserBySession();

    int getUserLimit();

    void userLimit(boolean usersLimiting);

    void setUserLimit(int usersLimit);

    int getUserCount();

}
