package com.pbl5.pbl5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.pbl5.pbl5.model.Test;
import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByTestType(String testType);
    
    @Query("SELECT t FROM Test t JOIN t.categories c WHERE c.id = :categoryId AND t.testType = :type")
    Optional<Test> findByCategoryIdAndType(@Param("categoryId") Long categoryId, @Param("type") String type);
    
    @Query("SELECT t FROM Test t JOIN t.categories c WHERE c.id = :categoryId")
    List<Test> findByCategoryId(@Param("categoryId") Long categoryId);
}