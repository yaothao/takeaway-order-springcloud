package com.yaothao.order.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    PARAM_ERROR(1, "参数错误"),
    CART_EMPTY(2, "购物车不能为空")
    ;

    ErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;


}
