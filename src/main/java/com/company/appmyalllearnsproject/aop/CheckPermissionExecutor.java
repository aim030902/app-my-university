package com.company.appmyalllearnsproject.aop;

import com.company.appmyalllearnsproject.entity.User;
import com.company.appmyalllearnsproject.exception.ForbiddenException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CheckPermissionExecutor {
    @Before(value = "@annotation(checkPermission)")
    public void checkPermissionMyMethod(CheckPermission checkPermission){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean exists = false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.permission())) {
                exists = true;
                break;
            }
        }
        if (!exists) throw new ForbiddenException(checkPermission.permission(), "Not Permission");
    }
}
