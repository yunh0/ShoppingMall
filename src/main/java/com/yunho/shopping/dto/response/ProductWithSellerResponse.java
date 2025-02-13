package com.yunho.shopping.dto.response;

import com.yunho.shopping.dto.ProductDto;

import java.time.LocalDateTime;

public record ProductWithSellerResponse(
        Long productId,
        String productName,
        Integer price,
        Integer count,
        LocalDateTime createdAt
) {
        public static ProductWithSellerResponse of(
                Long productId,
                String productName,
                Integer price,
                Integer count,
                LocalDateTime createdAt
        ){
            return new ProductWithSellerResponse(productId, productName, price, count, createdAt);
        }

        public static ProductWithSellerResponse from(ProductDto productDto){
            return ProductWithSellerResponse.of(
                    productDto.productId(),
                    productDto.productName(),
                    productDto.price(),
                    productDto.count(),
                    productDto.createdAt()
            );
        }
}
