package com.yunho.shopping.repository.querydsl;

import com.yunho.shopping.domain.PurchaseHistory;

import java.util.List;

public interface PurchaseHistoryRepositoryCustom {

    List<PurchaseHistory> findByUserIdOrderByCreatedAtDescTop3(String userId);
}
