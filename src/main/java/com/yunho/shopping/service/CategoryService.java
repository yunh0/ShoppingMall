package com.yunho.shopping.service;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("해당 카테고리를 찾을 수 없습니다."));
    }

    public List<Category> getCategoriesByDepth(int depth){
        return categoryRepository.findByDepth(depth);
    }

    public List<Category> getSubCategories(Long parendId){
        return categoryRepository.findByParentCategory_CategoryId(parendId);
    }
}
