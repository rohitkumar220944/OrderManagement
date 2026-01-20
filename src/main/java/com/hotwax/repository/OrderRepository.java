package com.hotwax.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotwax.entity.OrderHeader;

@Repository
public interface OrderRepository extends JpaRepository<OrderHeader, Integer> {
    List<OrderHeader> findByCustomer_CustomerId(Integer customerId);
}