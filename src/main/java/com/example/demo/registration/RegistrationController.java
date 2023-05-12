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

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping(path = "reset")
    public String reset(@RequestParam("email") String email){
        return appUserService.resetUserPassword(email);
    }

    @GetMapping(path = "resetPass")
    public String passwordReset(@RequestParam("token") String token){
        return "Шаблон сюда какой то засуну потом";
    }

    @PostMapping(path = "resetPass")
    public String passwordReset(@RequestParam("token") String token,
                                @RequestParam("newPass1") String newPass1,
                                @RequestParam("newPass2") String newPass2){
        return registrationService.passwordReset(token, newPass1, newPass2);
    }


}
