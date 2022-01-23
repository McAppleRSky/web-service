package ru.mrs.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mrs.demo.model.User;
import ru.mrs.demo.repository.UserRepository;
import ru.mrs.demo.service.usr.SecureContextPrincipalUser;
import ru.mrs.demo.service.usr.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecureContextPrincipalUser currentUser;

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        return userRepository.findByLogin(currentUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }

}
