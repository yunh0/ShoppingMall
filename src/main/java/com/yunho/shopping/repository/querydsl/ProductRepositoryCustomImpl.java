package com.yunho.shopping.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yunho.shopping.domain.Category;
import com.yunho.shopping.domain.Product;
import com.yunho.shopping.domain.QCategory;
import com.yunho.shopping.domain.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager entityManager){
        super(Product.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Product> findTop6ByTopCategory(Category topCategory) {
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        return queryFactory
                .selectFrom(product)
                .where(product.category.in(
                        queryFactory
                                .selectFrom(category)
                                .where(category.parentCategory.isNotNull())
                ))
                .fetch()
                .stream()
                .filter(p -> findRootCategory(p.getCategory()).equals(topCategory))
                .sorted(Comparator.comparing(Product::getCreatedAt).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }

    public Category findRootCategory(Category category) {
        while (category.getParentCategory() != null) {
            category = category.getParentCategory();
        }
        return category;
    }

    @Override
    public Optional<Product> findWithPessimisticLockById(Long productId) {
        QProduct product = QProduct.product;

        Product result = queryFactory
                .selectFrom(product)
                .where(product.productId.eq(productId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
