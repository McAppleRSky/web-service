package com.example.sweater.service;

import com.example.sweater.domain.RoleAndGrantedAuthority;
import com.example.sweater.domain.UserAndDetails;
import com.example.sweater.repos.UserRepo;
import com.example.sweater.service.helper.AppPropAndCreateUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
    public boolean addUser(UserAndDetails user){
        boolean isFindUserResult = true;
        UserAndDetails userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null){
            isFindUserResult=false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(RoleAndGrantedAuthority.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendMessage(user);
        return isFindUserResult;
    }

    private void sendMessage(UserAndDetails user) {
        if ( StringUtils.hasText(user.getEmail()) ){
            URL apphostUrl = mailSender.getApphostUrlPieces().createUrl("activation", user.getActivationCode());
            String message = String.format(
                    "Hello, %s! \n"
                    + "Welcome to Sweater. Please, visit next link: %s",
                    user.getUsername(),
                    apphostUrl.toString()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        UserAndDetails user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public List<UserAndDetails> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(UserAndDetails user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(RoleAndGrantedAuthority.values())
                .map(RoleAndGrantedAuthority::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for(String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles()
                        .add(RoleAndGrantedAuthority.valueOf(key));

            }
        }
        userRepo.save(user);
    }

    public void updateProfile(UserAndDetails user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged) {
            user.setEmail(email);
            if(StringUtils//.isEmpty.hasLength
                    .hasText(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (StringUtils.hasText(password)){
            user.setPassword(password);
        }
        userRepo.save(user);
        if (isEmailChanged){
            sendMessage(user);
        }
    }
}
