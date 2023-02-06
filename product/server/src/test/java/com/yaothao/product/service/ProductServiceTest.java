package com.yaothao.product.service;

import com.yaothao.product.ProductApplicationTests;
import com.yaothao.product.common.DecreaseStockInput;
import com.yaothao.product.common.ProductInfoOutPut;
import com.yaothao.product.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
class ProductServiceTest extends ProductApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    void findUpAll() throws Exception {
        List<ProductInfo> list = productService.findUpAll();
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    void decreaseStock() throws Exception {
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput("164103465734242707", 3);
        productService.decreaseStock(Arrays.asList(decreaseStockInput));


    }
}