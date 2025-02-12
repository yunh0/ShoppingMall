package com.yunho.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table
public class ProductImg extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImgId;

    @Setter
    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private ProductImg(String path, Product product){
        this.path = path;
        this. product = product;
    }

    protected ProductImg(){

    }

    public static ProductImg of(String path, Product product){
        return new ProductImg(path, product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getProductImgId());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof ProductImg that)) return false;
        return this.getProductImgId() != null && this.getProductImgId().equals(that.getProductImgId());
    }
}
