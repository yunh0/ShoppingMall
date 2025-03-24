package com.yunho.shopping.repository.querydsl;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    List<Product> findTop6ByTopCategory(Category category);
    Optional<Product> findWithPessimisticLockById(Long productId);
}
