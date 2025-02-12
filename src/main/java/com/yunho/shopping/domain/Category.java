package com.yunho.shopping.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CategoryId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> childCategory;

    private int depth;
}
