package com.uday.oauthsecuritysystem.controller;

import com.uday.oauthsecuritysystem.entity.AuditLog;
import com.uday.oauthsecuritysystem.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('AUDIT_READ')")
    public List<AuditLog> getAllLogs() {

        return auditLogRepository.findAll();
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('AUDIT_READ')")
    public List<AuditLog> getUserLogs(
            @PathVariable String username) {

        return auditLogRepository
                .findByUsername(username);
    }
}