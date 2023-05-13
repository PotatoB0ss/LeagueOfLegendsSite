package com.example.demo.passwordRecovery;

import com.example.demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "api/v1/recovery")
@AllArgsConstructor
public class RecoveryController {

    private final RecoveryService recoveryService;
    private final AppUserService appUserService;

    @PostMapping(path = "reset")
    public String reset(@RequestParam("email") String email){
        return appUserService.resetUserPassword(email);
    }

    @GetMapping(path = "resetPass")
    public ModelAndView passwordReset(@RequestParam("token") String token){
        if(recoveryService.tokenCheck(token)){
            return new ModelAndView("passwordReset/resetEmailPass");
        }
        return new ModelAndView("passwordReset/resetEmailPassWrong");
    }

    @PostMapping(path = "resetPass")
    public String passwordReset(@RequestParam("token") String token,
                                @RequestParam("newPass1") String newPass1,
                                @RequestParam("newPass2") String newPass2){
        return recoveryService.passwordReset(token, newPass1, newPass2);
    }


}
