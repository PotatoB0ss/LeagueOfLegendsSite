package com.example.demo.passwordRecovery.passwordResetToken;

import com.example.demo.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PasswordResetToken {

    @SequenceGenerator(
            name = "passwordReset_token_sequence",
            sequenceName = "passwordReset_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "passwordReset_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column
    private boolean used = false;


    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private AppUser appUser;

    public PasswordResetToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
    
}
