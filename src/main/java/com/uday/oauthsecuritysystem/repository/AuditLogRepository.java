package com.uday.oauthsecuritysystem.repository;

import com.uday.oauthsecuritysystem.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUsername(
            String username);
}