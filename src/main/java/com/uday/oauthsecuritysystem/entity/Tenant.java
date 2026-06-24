package com.uday.oauthsecuritysystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String tenantName;

    private String description;

    @OneToMany(mappedBy = "tenant")
    private Set<User> users = new HashSet<>();
}