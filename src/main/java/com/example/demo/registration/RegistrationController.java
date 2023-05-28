package com.example.demo.registration;


import com.example.demo.appuser.AppUserService;
import com.example.demo.captcha.RecaptchaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserService appUserService;
    private final RecaptchaService recaptchaService;


    @PostMapping()
    public String register(@ModelAttribute RegistrationRequest request, @RequestParam("recaptchaResponse") String recaptchaResponse) {
        boolean isValidRecaptcha = recaptchaService.verifyRecaptcha(recaptchaResponse);
        if (isValidRecaptcha) {
            return registrationService.register(request);
        } else {
            return "Error: incorrect reCAPTCHA";
        }
    }

    @GetMapping(path = "confirm")
    public RedirectView confirm(@RequestParam("token") String token) {
        if (registrationService.confirmToken(token).equals("confirmed")) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");
            return redirectView;
        }

        return null;
    }

    @PostMapping(path = "checkEmail")
    public boolean emailCheck(@RequestParam("email") String email){
        return appUserService.emailCheck(email);
    }

    @PostMapping(path = "checkPassword")
    public boolean passwordCheck(@RequestParam("password") String password){
        return appUserService.passwordCheck(password);
    }

    @PostMapping(path = "checkUsername")
    public boolean userNameCheck(@RequestParam("username") String username){
        return appUserService.userNameCheck(username);
    }
}
