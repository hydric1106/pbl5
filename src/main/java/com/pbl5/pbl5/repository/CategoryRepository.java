package com.pbl5.pbl5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pbl5.pbl5.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Các phương thức tìm kiếm bổ sung có thể được thêm vào đây
}