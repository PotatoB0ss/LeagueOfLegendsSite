package com.example.demo.passwordRecovery;


import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserService;
import com.example.demo.passwordRecovery.passwordResetToken.PasswordResetToken;
import com.example.demo.passwordRecovery.passwordResetToken.PasswordResetTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecoveryService {

    private final PasswordResetTokenService passwordResetTokenService;
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional
    public String passwordReset(String token, String newPass1, String newPass2){
        PasswordResetToken passwordResetToken = passwordResetTokenService
                .getPasswordResetToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));


        LocalDateTime expiredAt = passwordResetToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        if(passwordResetToken.isUsed()){
            throw new IllegalStateException("token has already used");
        }

        if(!newPass1.equals(newPass2)){
            throw new IllegalStateException("password mismatch");
        }

        AppUser appUser = (AppUser) appUserService.loadUserByUsername(passwordResetToken.getAppUser().getEmail());

        String encodedPassword = bCryptPasswordEncoder
                .encode(newPass1);
        appUser.setPassword(encodedPassword);
        appUserService.save(appUser);
        passwordResetTokenService.setUsed(token);
        return "Password has been successfully changed";
    }

    protected String tokenCheck(String token){
        PasswordResetToken passwordResetToken = passwordResetTokenService
                .getPasswordResetToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        LocalDateTime expiredAt = passwordResetToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            return "token expired";
        }

        if(passwordResetToken.isUsed()){
            return "token has already used";
        }


        return "the token is correct";
    }

}
