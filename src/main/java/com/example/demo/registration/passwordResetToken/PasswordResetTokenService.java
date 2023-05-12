package com.example.demo.registration.passwordResetToken;

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

}
