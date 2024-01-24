package com.project.hyperface_project.repo;

import com.project.hyperface_project.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepo extends JpaRepository<UserAuth,Integer> {
    public Optional<UserAuth> findByUsername(String username);
    public boolean existsByUsername(String username);
}
