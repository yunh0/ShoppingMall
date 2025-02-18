package com.yunho.shopping.repository;

import com.yunho.shopping.domain.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {

    List<ProductImg> findByProduct_ProductId(Long productId);
}
