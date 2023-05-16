package com.example.demo.registration;


import com.example.demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserService appUserService;


    @PostMapping()
    public String register(@ModelAttribute RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
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
