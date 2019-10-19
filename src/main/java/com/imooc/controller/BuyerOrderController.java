package com.imooc.controller;

import com.imooc.dto.OrderDTO;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.form.OrderForm;
import com.imooc.service.OrderService;
import com.imooc.utils.OrderForm2OrderDTOConverter;
import com.imooc.utils.ResultVOUtil;
import com.imooc.utils.SnowFlake;
import com.imooc.view.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhang Pengnian
 * @since 2019-10-19 13:19
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    private final OrderService orderService;

    private final SnowFlake snowFlake;

    public BuyerOrderController(OrderService orderService, SnowFlake snowFlake) {
        this.orderService = orderService;
        this.snowFlake = snowFlake;
    }

    /**
     * 创建订单
     *
     * @return Map<String, String>格式的ResultVO。
     */
    @PostMapping("/create")
    public ResultVO create(@Valid OrderForm orderForm,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm = {}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        orderDTO.setOrderId(snowFlake.nextId("Order"));
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);

    }

    /**
     * 根据Openid订单列表
     *
     * @return 订单列表
     */
    @PostMapping("/list")
    public ResultVO list(
            @RequestParam("openid") String openid,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        openid = openid.trim();
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultVOUtil.success(orderDTOPage.getContent());

    }

    /**
     * TODO openid未使用，需要用于安全保障
     *
     * 查看单个订单
     */
    @GetMapping("/detail")
    public ResultVO detail(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId) {

        OrderDTO orderDTO = orderService.findOne(orderId);
        return ResultVOUtil.success(orderDTO);

    }

    /**
     * TODO openid未使用，需要用于安全保障
     *
     * 取消订单
     */
    @PostMapping("/cancel")
    public ResultVO cancel(
            @RequestParam("openid") String openid,
            @RequestParam("orderId") String orderId) {

        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO cancelResult = orderService.cancel(orderDTO);
        return ResultVOUtil.success(cancelResult);

    }

}
