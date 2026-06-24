package com.uday.oauthsecuritysystem.service;

import com.uday.oauthsecuritysystem.entity.AuditLog;
import com.uday.oauthsecuritysystem.entity.User;
import com.uday.oauthsecuritysystem.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(
            User user,
            String action,
            String ipAddress,
            String status) {

        AuditLog auditLog =
                AuditLog.builder()
                        .user(user)
                        .username(
                                user != null
                                        ? user.getUsername()
                                        : null
                        )
                        .tenantId(
                                user != null
                                        && user.getTenant() != null
                                        ? user.getTenant()
                                          .getTenantName()
                                        : null
                        )
                        .action(action)
                        .ipAddress(ipAddress)
                        .status(status)
                        .build();

        auditLogRepository.save(auditLog);
    }
}