package com.yunho.shopping.service;

import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.Product;
import com.yunho.shopping.dto.ProductDto;
import com.yunho.shopping.repository.MemberRepository;
import com.yunho.shopping.repository.ProductImgRepository;
import com.yunho.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ProductImgRepository productImgRepository;

    public void saveProduct(ProductDto productDto){
        Member member = memberRepository.getReferenceById(productDto.memberDto().userId());

        Product product = productDto.toEntity(member);
        productRepository.save(product);
    }

}
