package com.imooc.utils;

import com.imooc.dto.OrderDTO;
import com.imooc.model.OrderMaster;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 22:05
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        PropertiesCopyUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream()
                .map(OrderMaster2OrderDTOConverter::convert)
                .collect(Collectors.toList());
    }

}
