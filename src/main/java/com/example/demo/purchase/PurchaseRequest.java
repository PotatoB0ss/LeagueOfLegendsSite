package com.example.demo.purchase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

public class PurchaseRequest {

    private final PaymentDataService paymentDataService;

    public PurchaseRequest(PaymentDataService paymentDataService) {
        this.paymentDataService = paymentDataService;
    }

    public String sendBuyRequest(Map<String, Object> product) {
        RestTemplate restTemplate = new RestTemplate();
        UUID uuid = UUID.randomUUID();

        // Payment Info
        Integer shopId = 706;
        Integer orderId = Math.abs(uuid.hashCode());
        Float amount = Float.valueOf(product.get("finalRUBPrice").toString());
        String token = "3d0614c9abf6e817f9cda5a8ea0b2056";

        // Additional Data JSON
        AdditionalData additionalData = new AdditionalData(product.get("prodName").toString(), product.get("email").toString(), product.get("amount").toString());

        // PaymentData to check it in the future
        PaymentData paymentData = new PaymentData(shopId, orderId, amount, token, additionalData);
        paymentDataService.savePaymentData(paymentData);

        ObjectMapper mapper = new ObjectMapper();
        String addData;
        try {
            addData = mapper.writeValueAsString(additionalData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("shop_id", shopId.toString());
        map.add("order_id", orderId.toString());
        map.add("amount", amount.toString());
        map.add("token", token);
        map.add("data", addData);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);

        String url = "https://lk.rukassa.pro/api/v1/create";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println(requestEntity);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                return jsonNode.get("url").asText();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Ошибка при выполнении запроса. Код ошибки: " + response.getStatusCodeValue());
        }

        return "/";
    }
}
