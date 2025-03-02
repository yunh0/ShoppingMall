package com.yunho.shopping.repository.querydsl;

import com.yunho.shopping.domain.Category;

import java.util.List;

public interface CategoryRepositoryCustom {

    List<Category> findByDepthIsOne();
}
