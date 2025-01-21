package com.yunho.shopping.domain.constant;

import lombok.Getter;

public enum Gender {
    MAN("남자"),
    WOMAN("여자");

    @Getter
    private final String description;

    Gender(String description){
        this.description = description;
    }
}
