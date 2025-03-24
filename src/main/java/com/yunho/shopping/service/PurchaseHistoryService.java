package com.yunho.shopping.service;

import com.yunho.shopping.domain.Product;
import com.yunho.shopping.domain.PurchaseHistory;
import com.yunho.shopping.repository.MemberRepository;
import com.yunho.shopping.repository.PurchaseHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final MemberRepository memberRepository;

    public void savePurchaseHistory(Product product, String userId, int quantity){
        purchaseHistoryRepository.save(
                PurchaseHistory.of(
                        memberRepository.getReferenceById(userId),
                        product,
                        quantity
                )
        );
    }
}
