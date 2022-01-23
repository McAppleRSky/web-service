package ru.mrs.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mrs.demo.model.User;
import ru.mrs.demo.repository.UserRepository;
import ru.mrs.demo.service.usr.SecureContextPrincipalUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

@ExtendWith(MockitoExtension.class)
class UserServiceGetCurrentUserTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private SecureContextPrincipalUser secureContextPrincipalUserMock;

    @Test
    public void test_getCurrentUser_null() {
        Optional<User> optional = Optional.ofNullable(null);
        Mockito.when( userRepositoryMock.findByLogin(nullable(String.class)) )
                .thenReturn(optional);
        IllegalArgumentException actualException = null;
        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser();
        } catch (IllegalArgumentException exception) {
            actualException=exception;
        }
        String expectedMessage = "user not found";
        assertEquals(expectedMessage, actualException.getMessage());
        assertNull(currentUser);
    }

    @Test
    public void test_getCurrentUser() {
        String expectedName = randomAlphabetic(7);
        User user = new User();
        user.setLogin(expectedName);
        Optional<User> optional = Optional.ofNullable(user);
        Mockito.when( userRepositoryMock.findByLogin(any(String.class)) )
                .thenReturn(optional);
        IllegalArgumentException actualException = null;
        User currentUser = null;
        try {
            currentUser = userService.getCurrentUser();
        } catch (IllegalArgumentException exception) {
            actualException=exception;
        }
        assertNull(actualException);
        assertNotNull(currentUser);
        assertEquals(expectedName, currentUser.getLogin());
    }

}
