package ru.mrs.demo.service.usr;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SecureContextPrincipalUserImpl implements SecureContextPrincipalUser{

    protected SecurityContext securityContext;
    protected Authentication authentication;
    protected org.springframework.security.core.userdetails.User principal;

    protected SecureContextPrincipalUserImpl(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    public String getUsername(){
        Object isRealContext = null;
        if (securityContext == isRealContext)
            securityContext = SecurityContextHolder.getContext();
            authentication = securityContext.getAuthentication();
            principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return principal.getUsername();
    }

}
