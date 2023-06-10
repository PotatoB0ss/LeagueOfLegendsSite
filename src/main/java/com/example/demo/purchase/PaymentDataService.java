package com.example.demo.purchase;


import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentDataService {

    private final PaymentDataRepository paymentDataRepository;

    public void savePaymentData(PaymentData paymentData){
        paymentDataRepository.save(paymentData);
    }

    public Optional<PaymentData> getPaymentData(String order_id){
        return paymentDataRepository.findByOrderId(order_id);
    }

}
