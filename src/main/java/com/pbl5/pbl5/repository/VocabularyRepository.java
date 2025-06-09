package com.pbl5.pbl5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pbl5.pbl5.model.Vocabulary;
import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {
    List<Vocabulary> findByCategoryId(Integer categoryId);
}