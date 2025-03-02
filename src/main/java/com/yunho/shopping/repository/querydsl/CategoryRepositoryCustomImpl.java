package com.yunho.shopping.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.QCategory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryCustomImpl extends QuerydslRepositorySupport implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryCustomImpl(EntityManager entityManager){
        super(Category.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Category> findByDepthIsOne() {
        return queryFactory
                .selectFrom(QCategory.category)
                .where(QCategory.category.depth.eq(1))
                .fetch();
    }
}
