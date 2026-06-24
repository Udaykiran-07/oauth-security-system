package com.uday.oauthsecuritysystem.service;

import com.uday.oauthsecuritysystem.entity.User;
import com.uday.oauthsecuritysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountLockService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MINUTES = 30;

    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public void loginSucceeded(User user) {

        user.setFailedAttempts(0);

        userRepository.save(user);
    }

    public void loginFailed(User user) {

        int attempts =
                user.getFailedAttempts() + 1;

        user.setFailedAttempts(attempts);

        if (attempts >= MAX_ATTEMPTS) {

            user.setAccountLocked(true);
            user.setAccountNonLocked(false);
            user.setLockTime(LocalDateTime.now());
        }

        userRepository.save(user);
    }

    public boolean unlockWhenTimeExpired(
            User user) {

        if (!Boolean.TRUE.equals(
                user.getAccountLocked())) {

            return true;
        }

        if (user.getLockTime() == null) {

            return false;
        }

        LocalDateTime unlockTime =
                user.getLockTime()
                        .plusMinutes(
                                LOCK_DURATION_MINUTES);

        if (LocalDateTime.now()
                .isAfter(unlockTime)) {

            user.setAccountLocked(false);
            user.setAccountNonLocked(true);
            user.setFailedAttempts(0);
            user.setLockTime(null);

            userRepository.save(user);

            auditLogService.log(
                    user,
                    "ACCOUNT_UNLOCKED",
                    "127.0.0.1",
                    "SUCCESS"
            );

            return true;
        }

        return false;
    }
}