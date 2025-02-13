package com.yunho.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table
public class Product extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Setter
    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String info;

    @Column(nullable = false)
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member member;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ProductImg> images;

    private Product(String productName, Integer price, String info, Integer count, Category category, Member member){
        this.productName = productName;
        this.price = price;
        this.info = info;
        this.count = count;
        this.category = category;
        this.member = member;
    }

    protected Product(){

    }

    public static Product of(String productName, Integer price, String info, Integer count, Category category, Member member){
        return new Product(productName, price, info, count, category, member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getProductId());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Product that)) return false;
        return this.getProductId() != null && this.getProductId().equals(that.getProductId());
    }
}
