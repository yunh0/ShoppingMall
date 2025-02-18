package com.yunho.shopping.dto.response;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.ProductImg;
import com.yunho.shopping.dto.ProductDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record ProductResponse(
        Long productId,
        String productName,
        Integer price,
        String info,
        Integer count,
        Category category,
        LocalDateTime createdAt,
        List<ProductImg> images
) {
    public static ProductResponse of(
            Long productId,
            String productName,
            Integer price,
            String info,
            Integer count,
            Category category,
            LocalDateTime createdAt,
            List<ProductImg> images
    ){
        return new ProductResponse(
                productId,
                productName,
                price,
                info,
                count,
                category,
                createdAt,
                images
        );
    }

    public static ProductResponse from(ProductDto dto, List<ProductImg> images){
        return ProductResponse.of(
                dto.productId(),
                dto.productName(),
                dto.price(),
                dto.info(),
                dto.count(),
                dto.category(),
                dto.createdAt(),
                images
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
