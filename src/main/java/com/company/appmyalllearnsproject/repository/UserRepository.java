package com.company.appmyalllearnsproject.repository;

import com.company.appmyalllearnsproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndEmailCode(String email, String emailCode);

}
