package com.yaothao.order.repository;

import com.yaothao.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMaterRepository extends JpaRepository<OrderMaster, String> {
}
