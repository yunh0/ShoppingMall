package com.yunho.shopping.service;

import com.yunho.shopping.domain.Product;
import com.yunho.shopping.domain.PurchaseHistory;
import com.yunho.shopping.dto.response.PurchaseHistoryResponse;
import com.yunho.shopping.repository.MemberRepository;
import com.yunho.shopping.repository.PurchaseHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final MemberRepository memberRepository;
    private final ProductImgService productImgService;

    public void savePurchaseHistory(Product product, String userId, int quantity){
        purchaseHistoryRepository.save(
                PurchaseHistory.of(
                        memberRepository.getReferenceById(userId),
                        product,
                        quantity
                )
        );
    }

    public List<PurchaseHistoryResponse> getPurchaseHistoryOrderByCreatedAtTop3(String userId){
        return purchaseHistoryRepository.findByUserIdOrderByCreatedAtDescTop3(userId).stream()
                .map(purchaseHistory -> PurchaseHistoryResponse.from(purchaseHistory,
                        productImgService.getProductImagesPath(
                                productImgService.getProductImages(purchaseHistory.getProduct().getProductId())
                        )
                ))
                .collect(Collectors.toList());
    }

    public Page<PurchaseHistoryResponse> getPurchaseHistoryWithPaging(String userId, Pageable pageable){
        return purchaseHistoryRepository.findByMember_UserIdContaining(userId, pageable)
                .map(purchaseHistory -> PurchaseHistoryResponse.from(purchaseHistory,
                        productImgService.getProductImagesPath(
                                productImgService.getProductImages(purchaseHistory.getProduct().getProductId())
                        )
                ));
    }
}
