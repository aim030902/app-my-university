package com.company.appmyalllearnsproject.config;

import com.company.appmyalllearnsproject.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals("" + authentication.getPrincipal()))){
            Object principal = authentication.getPrincipal();
            System.out.println("principal = " + principal);

            User user = (User) authentication.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }
}
