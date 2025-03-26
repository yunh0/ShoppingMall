package com.yunho.shopping.dto.response;

import com.yunho.shopping.domain.PurchaseHistory;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProductDto;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseHistoryResponse(
        MemberDto memberDto,
        ProductResponse productResponse,
        Integer quantity,
        LocalDateTime createdAt
) {
    public static PurchaseHistoryResponse of(
            MemberDto memberDto,
            ProductResponse productResponse,
            Integer quantity,
            LocalDateTime createdAt
    ){
        return new PurchaseHistoryResponse(memberDto, productResponse, quantity, createdAt);
    }

    public static PurchaseHistoryResponse from(PurchaseHistory entity, List<String> imagesPath){
        return PurchaseHistoryResponse.of(
                MemberDto.from(entity.getMember()),
                ProductResponse.from(ProductDto.from(entity.getProduct()), imagesPath),
                entity.getQuantity(),
                entity.getCreatedAt()
        );
    }
}
