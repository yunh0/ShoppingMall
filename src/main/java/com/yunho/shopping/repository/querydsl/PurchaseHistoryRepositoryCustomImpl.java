package com.yunho.shopping.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunho.shopping.domain.PurchaseHistory;
import com.yunho.shopping.domain.QPurchaseHistory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseHistoryRepositoryCustomImpl extends QuerydslRepositorySupport implements PurchaseHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PurchaseHistoryRepositoryCustomImpl(EntityManager entityManager){
        super(PurchaseHistory.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<PurchaseHistory> findByUserIdOrderByCreatedAtDescTop3(String userId) {
        QPurchaseHistory purchaseHistory = QPurchaseHistory.purchaseHistory;

        return queryFactory
                .selectFrom(purchaseHistory)
                .where(purchaseHistory.member.userId.eq(userId))
                .orderBy(purchaseHistory.createdAt.desc())
                .limit(3)
                .fetch();
    }
}
