package com.yaothao.order.controller;

import com.yaothao.order.VO.ResultVO;
import com.yaothao.order.converter.OrderForm2OrderDTO;
import com.yaothao.order.dto.OrderDTO;
import com.yaothao.order.enums.ErrorCodeEnum;
import com.yaothao.order.exception.OrderException;
import com.yaothao.order.form.OrderForm;
import com.yaothao.order.service.OrderService;
import com.yaothao.order.utils.ResultVOUtil;
import com.yaothao.product.client.ProductClient;
import com.yaothao.product.common.VO.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;

/*    @GetMapping("/test")
    public String testFeignClient() {
        log.info("response={}", (Object) productClient.list());
        return "ok";
    }*/

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("error creating order " + orderForm);
            throw new OrderException(ErrorCodeEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[creating order] cart is empty " + orderDTO.getOrderDetailList());
            throw new OrderException(ErrorCodeEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultVOUtil.success(map);

    }


}
