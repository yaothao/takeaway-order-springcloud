package com.yaothao.product.client;

import com.yaothao.product.common.DecreaseStockInput;
import com.yaothao.product.common.ProductInfoOutPut;
import com.yaothao.product.common.VO.ProductVO;
import com.yaothao.product.common.VO.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @PostMapping("/product/listForOrder")
    public List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList);

    @GetMapping("product/list")
    public ResultVO<ProductVO> list();
}
