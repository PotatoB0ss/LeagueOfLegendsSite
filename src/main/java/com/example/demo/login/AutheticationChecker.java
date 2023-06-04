package com.example.demo.login;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Objects;

public class AutheticationChecker {
    public boolean authenticationCheck(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !Objects.equals(authentication.getName(), "anonymousUser")) {

            AppUser user = (AppUser) authentication.getPrincipal();

            model.addAttribute("authenticated", true);
            model.addAttribute("username", user.getName());
            return true;
        } else {
            model.addAttribute("authenticated", false);
            return false;
        }
    }
}
