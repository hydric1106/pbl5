package com.pbl5.pbl5.repository;

import com.pbl5.pbl5.model.FlashcardsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardsCategoryRepository extends JpaRepository<FlashcardsCategory, Integer> {
    @Query("SELECT fc FROM FlashcardsCategory fc WHERE fc.flccategoryId IN (SELECT f.flccategoryId FROM Flashcard f WHERE f.userId = :userId)")
    List<FlashcardsCategory> findAllByUserId(@Param("userId") Integer userId);
}