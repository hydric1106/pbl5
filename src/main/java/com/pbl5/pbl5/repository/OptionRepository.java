package com.pbl5.pbl5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pbl5.pbl5.model.Option;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByQuestionId(Long questionId);
}