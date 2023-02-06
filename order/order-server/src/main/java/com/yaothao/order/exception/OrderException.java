package com.yaothao.order.exception;

import com.yaothao.order.enums.ErrorCodeEnum;
import org.hibernate.criterion.Order;

public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(Integer order, String message) {
        super(message);
        this.code = code;
    }

    public OrderException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.code = errorCodeEnum.getCode();
    }
}
