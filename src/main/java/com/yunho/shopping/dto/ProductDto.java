package com.yunho.shopping.dto;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.Product;

import java.time.LocalDateTime;

public record ProductDto(
        Long productId,
        String productName,
        Integer price,
        String info,
        Integer count,
        Category category,
        MemberDto memberDto,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ProductDto of(
            String productName,
            Integer price,
            String info,
            Integer count,
            Category category,
            MemberDto memberDto
    ){
        return ProductDto.of(null, productName, price, info, count, category, memberDto);
    }

    public static ProductDto of(
            Long productId,
            String productName,
            Integer price,
            String info,
            Integer count,
            Category category,
            MemberDto memberDto
    ){
        return ProductDto.of(productId, productName, price, info, count, category, memberDto, null, null, null, null);
    }

    public static ProductDto of(
            Long productId,
            String productName,
            Integer price,
            String info,
            Integer count,
            Category category,
            MemberDto memberDto,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ){
        return new ProductDto(productId, productName, price, info, count, category, memberDto, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ProductDto from(Product product){
        return ProductDto.of(
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getInfo(),
                product.getCount(),
                product.getCategory(),
                MemberDto.from(product.getMember()),
                product.getCreatedAt(),
                product.getCreatedBy(),
                product.getModifiedAt(),
                product.getModifiedBy()
        );
    }

    public Product toEntity(Member member){
        return Product.of(
                productName,
                price,
                info,
                count,
                category,
                member
        );
    }
}
