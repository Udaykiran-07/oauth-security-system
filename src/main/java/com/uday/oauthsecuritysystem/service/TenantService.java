package com.uday.oauthsecuritysystem.service;

import com.uday.oauthsecuritysystem.entity.Tenant;
import com.uday.oauthsecuritysystem.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public Tenant createTenant(
            String tenantName,
            String description) {

        Tenant tenant = Tenant.builder()
                .tenantName(tenantName)
                .description(description)
                .build();

        return tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants() {

        return tenantRepository.findAll();
    }

    public Tenant getTenantById(Long id) {

        return tenantRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Tenant not found"));
    }
}