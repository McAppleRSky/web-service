package ru.mrs.demo.service.usr;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

class SecureContextPrincipalUserImplTest extends SecureContextPrincipalUserImpl{


    protected SecureContextPrincipalUserImplTest() {
        super(Mockito.mock(SecurityContext.class));
    }

    @Test
    public void test_getCurrentUser_null() {
        String testUsername = randomAlphabetic(7);
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        org.springframework.security.core.userdetails.User
                userMock = Mockito.mock(org.springframework.security.core.userdetails.User.class);
        Mockito.when(securityContext.getAuthentication())
                .thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal())
                .thenReturn(userMock);
        Mockito.when(userMock.getUsername())
                .thenReturn(testUsername);
        SecurityContext testContext = SecurityContextHolder.getContext();
        assertNotNull(testContext);
        getUsername();
        Mockito.verify(this.securityContext).getAuthentication();
        Mockito.verify(this.authentication).getPrincipal();
        Mockito.verify(this.principal).getUsername();
    }

}
