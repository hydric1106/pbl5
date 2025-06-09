package com.pbl5.pbl5.repository;

import com.pbl5.pbl5.model.UserProgress;
import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    Optional<UserProgress> findByUserAndTest(User user, Test test);
    List<UserProgress> findByUser(User user);
}