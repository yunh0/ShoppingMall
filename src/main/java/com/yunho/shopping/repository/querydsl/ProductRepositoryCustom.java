package com.yunho.shopping.repository.querydsl;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findTop6ByTopCategory(Category category);
}
