package com.example.demo.login;

import com.example.demo.captcha.RecaptchaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
@Slf4j
public class LoginController {

    private DaoAuthenticationProvider daoAuthenticationProvider;
    private final RecaptchaService recaptchaService;

    @PostMapping()
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("recaptchaResponse") String recaptchaResponse){
        boolean isValidRecaptcha = recaptchaService.verifyRecaptcha(recaptchaResponse);
        if (isValidRecaptcha) {
            try {
                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(email, password);

                Authentication authentication = daoAuthenticationProvider.authenticate(authRequest);

                log.debug("==AUTHENTICATION==\n{}\n ==END==", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return "redirect:/";
            }
            catch (AuthenticationException e){
                return "redirect:/login?error=IncorrectData";
            }
        } else {
            return "Error: incorrect reCAPTCHA";
        }
    }
}
