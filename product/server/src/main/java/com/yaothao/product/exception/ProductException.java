package com.yaothao.product.exception;

import com.yaothao.product.enums.ErrorCodeEnum;

public class ProductException extends RuntimeException{

    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.PRODUCT_NOT_EXIST.getMsg());
        this.code = errorCodeEnum.PRODUCT_NOT_EXIST.getCode();
    }

}
