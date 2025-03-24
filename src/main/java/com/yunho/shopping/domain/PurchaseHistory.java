package com.yunho.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table
public class PurchaseHistory extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseHistoryId;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private PurchaseHistory(Member member, Product product, Integer quantity){
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    protected PurchaseHistory(){

    }

    public static PurchaseHistory of(Member member, Product product, Integer quantity){
        return new PurchaseHistory(member, product, quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getPurchaseHistoryId());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof PurchaseHistory that)) return false;
        return this.getPurchaseHistoryId() != null && this.getPurchaseHistoryId().equals(that.getPurchaseHistoryId());
    }

}
