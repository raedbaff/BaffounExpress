package com.raed.baffounexpress.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raed.baffounexpress.user.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
