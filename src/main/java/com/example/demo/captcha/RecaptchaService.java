package com.example.demo.captcha;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6LcWdjEmAAAAAM9LiWETJRGqotowSXdNjtToo09z";

    private final RestTemplate restTemplate;
    private final Gson gson;

    public RecaptchaService() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public boolean verifyRecaptcha(String recaptchaResponse) {
        String url = RECAPTCHA_URL + "?secret=" + SECRET_KEY + "&response=" + recaptchaResponse;
        String responseBody = restTemplate.getForObject(url, String.class);
        RecaptchaResponse recaptchaResult = gson.fromJson(responseBody, RecaptchaResponse.class);
        return recaptchaResult.isSuccess();
    }
}


