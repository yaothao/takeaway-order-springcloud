package com.yaothao.order.controller;


import com.yaothao.product.client.ProductClient;
import com.yaothao.product.common.ProductInfoOutPut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

/*    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }*/

/*    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
        log.info("response={}", productInfoList);
        return "ok";
    }*/

    @GetMapping("/productDecreaseStock")
    public List<ProductInfoOutPut> productDecreaseStock() {
        List<ProductInfoOutPut> productInfoList = productClient.listForOrder(Arrays.asList("157875196366160022"));
        return productInfoList;
    }

}
