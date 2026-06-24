package com.uday.oauthsecuritysystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog extends BaseEntity {

    private String username;

    private String action;

    private String ipAddress;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String tenantId;
}