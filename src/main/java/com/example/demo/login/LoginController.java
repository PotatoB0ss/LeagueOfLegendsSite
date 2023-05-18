package com.example.demo.login;


import lombok.AllArgsConstructor;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Most likely I will change controller type from Controller to Rest Controller so that I can do AJAX requests for login
    // For example if login successful I will redirect on the main page
    // However if login is not successful I won't update the page in this situation via AJAX request --
    // -- I want to display on the page that login or password is incorrect

    @PostMapping()
    public String login(@RequestParam("email") String email, @RequestParam("password") String password){
        try {
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = daoAuthenticationProvider.authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/main";
        }
        catch (AuthenticationException e){
            // Ошибки тут обрабатывать мне по идеи не понадобиться так как redirect не будет
            // Тк контролер я поменяю на Rest Controller и в завимости от ответа который я получу в AJAX запросе
            // Я либо перекину пользователя на мейн страницу либо на той же где то под кнопкой логина напишу
            // напишу что юзер дубина и логин либо пароль неверны
            return "redirect:/login?error";
        }

    }
}
