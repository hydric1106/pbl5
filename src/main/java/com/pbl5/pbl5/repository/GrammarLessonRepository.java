package com.pbl5.pbl5.repository;

import com.pbl5.pbl5.model.GrammarLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarLessonRepository extends JpaRepository<GrammarLesson, Integer> {
}