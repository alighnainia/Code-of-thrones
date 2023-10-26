package com.project.authentification.Repositories;

import com.project.authentification.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByNom(String nom);


    Boolean existsByNom(String nom);

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    long countByEnabled(boolean enabled);
}
