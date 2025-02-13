package com.yunho.shopping.dto.request;

import com.yunho.shopping.domain.Category;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "상품명을 입력해주세요.")
    private String productName;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotBlank(message = "상품 설명을 입력해주세요.")
    private String info;

    @NotNull(message = "상품 수량을 입력해주세요.")
    private Integer count;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Long categoryId;

    public ProductDto toDto(MemberDto memberDto, Category category){
        return ProductDto.of(
                productName,
                price,
                info,
                count,
                category,
                memberDto
        );
    }
}
