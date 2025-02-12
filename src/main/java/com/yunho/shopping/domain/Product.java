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

    @Column(nullable = false)
    private String seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductImg> images;

    private Product(String productName, Integer price, String info, Integer count, String seller, Category category){
        this.productName = productName;
        this.price = price;
        this.info = info;
        this.count = count;
        this.seller = seller;
        this.category = category;
    }

    protected Product(){

    }

    public Product of(String productName, Integer price, String info, Integer count, String seller, Category category){
        return new Product(productName, price, info, count, seller, category);
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
