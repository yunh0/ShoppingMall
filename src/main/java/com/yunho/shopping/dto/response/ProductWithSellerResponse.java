package com.yunho.shopping.dto.response;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.dto.ProductDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record ProductWithSellerResponse(
        Long productId,
        String productName,
        Integer price,
        Integer count,
        Category category,
        LocalDateTime createdAt
) {
        public static ProductWithSellerResponse of(
                Long productId,
                String productName,
                Integer price,
                Integer count,
                Category category,
                LocalDateTime createdAt
        ){
            return new ProductWithSellerResponse(productId, productName, price, count, category, createdAt);
        }

        public static ProductWithSellerResponse from(ProductDto productDto){
            return ProductWithSellerResponse.of(
                    productDto.productId(),
                    productDto.productName(),
                    productDto.price(),
                    productDto.count(),
                    productDto.category(),
                    productDto.createdAt()
            );
        }

        public String categoryHierarchy() {
            return getCategoryHierarchy(this.category);
        }

        private String getCategoryHierarchy(Category category) {
            List<String> hierarchy = new ArrayList<>();
            while (category != null) {
                hierarchy.add(category.getName());
                category = category.getParentCategory();
            }
            Collections.reverse(hierarchy);
            return String.join(" - ", hierarchy);
        }
}
