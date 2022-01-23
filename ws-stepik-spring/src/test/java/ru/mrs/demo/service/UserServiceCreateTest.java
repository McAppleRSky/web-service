package ru.mrs.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mrs.demo.model.User;
import ru.mrs.demo.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceCreateTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Test
    public void createTest() {
        User user = new User();
        user.setPassword("Qwerty123!");
        String encodedPassword = "jf2w0jisdlfnweijdf0qjiedsolkfmwepofdjmsd";
        Mockito.doReturn(encodedPassword)
                .when(passwordEncoderMock)
                .encode(user.getPassword());

        userService.create(user);

        Mockito.verify(userRepositoryMock)
                .save(user);
        assertEquals(encodedPassword, user.getPassword());
    }

}
