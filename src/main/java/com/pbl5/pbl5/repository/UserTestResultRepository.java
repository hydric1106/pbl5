package com.pbl5.pbl5.repository;

import com.pbl5.pbl5.model.UserTestResult;
import com.pbl5.pbl5.model.User;
import com.pbl5.pbl5.model.Test;
import com.pbl5.pbl5.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserTestResultRepository extends JpaRepository<UserTestResult, Long> {
    List<UserTestResult> findByUser(User user);
    List<UserTestResult> findByUserAndTest(User user, Test test);
    List<UserTestResult> findAllByUserAndTestAndQuestion(User user, Test test, Question question);
}