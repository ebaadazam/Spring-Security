package com.ebaad.SpringSecurityProject.repository;

import com.ebaad.SpringSecurityProject.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username); // We used Optional<> as sometimes user exists or sometimes not

}
