package com.yunho.shopping.dto.request;

import com.yunho.shopping.dto.ProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequest {

    @NotBlank(message = "상품명을 입력해주세요.")
    private String productName;

    @NotNull(message = "가격을 입력해주세요.")
    private Integer price;

    @NotBlank(message = "상품 설명을 입력해주세요.")
    private String info;

    @NotNull(message = "상품 수량을 입력해주세요.")
    private Integer count;

    public static UpdateProductRequest from(ProductDto productDto){
        return new UpdateProductRequest(
                productDto.productName(),
                productDto.price(),
                productDto.info(),
                productDto.count()
        );
    }
}
