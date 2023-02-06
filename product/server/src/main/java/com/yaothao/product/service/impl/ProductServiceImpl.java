package com.yaothao.product.service.impl;

import com.google.gson.Gson;
import com.yaothao.product.common.DecreaseStockInput;
import com.yaothao.product.common.ProductInfoOutPut;
import com.yaothao.product.dataobject.ProductInfo;
import com.yaothao.product.enums.ErrorCodeEnum;
import com.yaothao.product.enums.ProductStatusEnum;
import com.yaothao.product.exception.ProductException;
import com.yaothao.product.repository.ProductInfoRepository;
import com.yaothao.product.service.ProductService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutPut> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutPut productInfoOutPut = new ProductInfoOutPut();
                    BeanUtils.copyProperties(e, productInfoOutPut);
                    return productInfoOutPut;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);

        List<ProductInfoOutPut> outPuts = productInfoList.stream().map(e -> {
            ProductInfoOutPut output = new ProductInfoOutPut();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());

        amqpTemplate.convertAndSend("productInfo", new Gson().toJson(outPuts));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputList) {
            Optional<ProductInfo> optionalProductInfo = productInfoRepository.findById(decreaseStockInput.getProductId());
            // 商品是否存在
            if (!optionalProductInfo.isPresent()) {
                throw new ProductException(ErrorCodeEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo = optionalProductInfo.get();
            Integer availableStock = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            // 库存是否不足
            if(availableStock < 1) {
                throw new ProductException(ErrorCodeEnum.PRODUCT_STOCK_EMPTY);
            }

            productInfo.setProductStock(availableStock);
            productInfoRepository.save(productInfo);

            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
