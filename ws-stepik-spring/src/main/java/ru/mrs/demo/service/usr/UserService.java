package ru.mrs.demo.service.usr;

import ru.mrs.demo.model.User;

public interface UserService {

    User create(User user);

    User getCurrentUser();

}
