package com.pbl5.pbl5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.pbl5.pbl5.model.Question;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.test.id = :testId")
    List<Question> findByTestId(@Param("testId") Long testId);
}