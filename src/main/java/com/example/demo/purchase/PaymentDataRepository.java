package com.example.demo.purchase;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentDataRepository extends JpaRepository<PaymentData, Long> {

    @Query("SELECT p FROM PaymentData p WHERE p.order_id = :orderId")
    Optional<PaymentData> findByOrderId(String orderId);
}
