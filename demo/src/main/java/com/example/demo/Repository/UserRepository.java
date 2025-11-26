package com.levelup.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.levelup.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
