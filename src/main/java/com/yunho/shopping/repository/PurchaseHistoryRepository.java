package com.yunho.shopping.repository;

import com.yunho.shopping.domain.PurchaseHistory;
import com.yunho.shopping.repository.querydsl.PurchaseHistoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends
        JpaRepository<PurchaseHistory, Long>,
        PurchaseHistoryRepositoryCustom
{
}
