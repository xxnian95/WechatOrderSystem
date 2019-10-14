package com.imooc.repository;

import com.imooc.model.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 00:37
 */
public interface OrderMasterRepository
        extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid,
                                        Pageable pageable);
}
