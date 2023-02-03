package com.company.appmyalllearnsproject.component;

import com.company.appmyalllearnsproject.entity.Role;
import com.company.appmyalllearnsproject.entity.User;
import com.company.appmyalllearnsproject.entity.enums.Permission;
import com.company.appmyalllearnsproject.repository.RoleRepository;
import com.company.appmyalllearnsproject.repository.UserRepository;
import com.company.appmyalllearnsproject.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Value("${spring.sql.init.mode}")
    private String initialModeType;

    @Override
    public void run(String... args) throws Exception {
        if (initialModeType.equals("always")){
            Permission[] permissions = Permission.values();
            Role roleAdmin = new Role(AppConstants.ADMIN, Arrays.asList(permissions), "Super user");
            Role roleUser = new Role(AppConstants.USER, Arrays.asList(
                    Permission.ADD_COMMENT,
                    Permission.EDIT_COMMENT,
                    Permission.DELETE_MY_COMMENT),
                    "Simple user"
            );
            roleRepository.saveAll(List.of(roleAdmin, roleUser));
            User admin = new User("Admin", "admin", passwordEncoder.encode("123"), roleAdmin, null, true);
            User user = new User("User", "user", passwordEncoder.encode("123"), roleUser, null, true);
            userRepository.saveAll(List.of(admin, user));
        }
    }
}
