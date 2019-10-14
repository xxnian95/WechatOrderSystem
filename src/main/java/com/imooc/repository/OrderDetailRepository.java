package com.imooc.repository;

import com.imooc.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 00:41
 */
public interface OrderDetailRepository
        extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrOrderId(String orderId);
}
