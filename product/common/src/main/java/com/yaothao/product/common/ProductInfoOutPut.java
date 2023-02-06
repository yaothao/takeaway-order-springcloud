package com.yaothao.product.common;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoOutPut {

    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    private Integer productStatus;
    private Integer categoryType;
}
