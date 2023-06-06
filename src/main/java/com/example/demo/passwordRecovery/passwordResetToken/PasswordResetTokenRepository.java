package com.example.demo.passwordRecovery.passwordResetToken;

import com.example.demo.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PasswordResetTokenRepository
extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByAppUser(AppUser appUser);

    @Transactional
    @Modifying
    @Query("UPDATE PasswordResetToken c " +
            "SET c.used = true " +
            "WHERE c.token = ?1")
    int updateUsed(String token);

    @Transactional
    @Modifying
    @Query("DELETE FROM PasswordResetToken c WHERE c.token = ?1")
    int deleteByToken(String token);

}
