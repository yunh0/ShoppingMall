package com.yunho.shopping.repository;

import com.yunho.shopping.domain.Product;
import com.yunho.shopping.repository.querydsl.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends
        JpaRepository<Product, Long>,
        ProductRepositoryCustom
{

    Page<Product> findByMember_UserIdContaining(String userId, Pageable pageable);
    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);
}
