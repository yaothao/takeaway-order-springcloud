package com.yaothao.order.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "phone cannot be empty")
    private String phone;

    @NotEmpty(message = "address cannot be empty")
    private String address;

    @NotEmpty(message = "openId cannot be empty")
    private String openid;

    @NotEmpty(message = "item cannot be empty")
    private String items;
}
