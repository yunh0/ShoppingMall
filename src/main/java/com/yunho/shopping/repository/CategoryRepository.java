package com.yunho.shopping.repository;

import com.yunho.shopping.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByDepth(int depth);

    List<Category> findByParentCategory_CategoryId(Long parentId);
}
