package com.yaothao.order.service.impl;

import com.yaothao.order.dataobject.OrderDetail;
import com.yaothao.order.dataobject.OrderMaster;
import com.yaothao.order.dto.OrderDTO;
import com.yaothao.order.enums.OrderStatusEnum;
import com.yaothao.order.enums.PayStatusEnum;
import com.yaothao.order.repository.OrderDetailRepository;
import com.yaothao.order.repository.OrderMaterRepository;
import com.yaothao.order.service.OrderService;
import com.yaothao.order.utils.KeyUtil;
import com.yaothao.product.client.ProductClient;
import com.yaothao.product.common.DecreaseStockInput;
import com.yaothao.product.common.ProductInfoOutPut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMaterRepository orderMaterRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        // 查询商品
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<String> orderIdList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            orderIdList.add(orderDetail.getProductId());
        }
        List<ProductInfoOutPut> productInfoList = productClient.listForOrder(orderIdList);

        // 计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDetailList) {
            for (ProductInfoOutPut productInfo : productInfoList) {
                if (orderDetail.getProductId().equals(productInfo.getProductId())) {
                    orderAmount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    // 订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

        // 扣库存
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> {
                    DecreaseStockInput decreaseStockInput =
                            new DecreaseStockInput(e.getProductId(), e.getProductQuantity());
                    return decreaseStockInput;
                })
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);

        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaterRepository.save(orderMaster);
        return orderDTO;
    }
}
