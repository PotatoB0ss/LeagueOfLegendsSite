package com.example.demo.login;


import lombok.AllArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
@Slf4j
public class LoginController {


    private DaoAuthenticationProvider daoAuthenticationProvider;


    // Most likely I will change controller type from Controller to Rest Controller so that I can do AJAX requests for login
    // For example if login successful I will redirect on the main page
    // However if login is not successful I won't update the page in this situation via AJAX request --
    // -- I want to display on the page that login or password is incorrect

    @PostMapping()
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                        HttpServletRequest request){
        try {
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(email, password);


            HttpSession session = request.getSession(true);

            Authentication authentication = daoAuthenticationProvider.authenticate(authRequest);

            log.debug("==AUTHENTICATION==\n{}\n ==END==", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            session.setAttribute("authentication", authentication);

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
