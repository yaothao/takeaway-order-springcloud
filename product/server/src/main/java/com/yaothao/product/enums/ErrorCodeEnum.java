package com.yaothao.product.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    PRODUCT_NOT_EXIST(1, "商品不存在"),
    PRODUCT_STOCK_EMPTY(2, "库存不足")
    ;

    private Integer code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
