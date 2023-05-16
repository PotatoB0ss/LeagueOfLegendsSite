package com.example.demo.passwordRecovery.passwordResetToken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;


    public void savePasswordResetToken(PasswordResetToken passwordResetToken){
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public Optional<PasswordResetToken> getPasswordResetToken(String passwordResetToken){
        return passwordResetTokenRepository.findByToken(passwordResetToken);
    }

    public int setUsed(String token){
        return passwordResetTokenRepository.updateUsed(token);
    }

}