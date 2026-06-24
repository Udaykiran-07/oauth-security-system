package com.uday.oauthsecuritysystem.controller;

import com.uday.oauthsecuritysystem.entity.Tenant;
import com.uday.oauthsecuritysystem.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public Tenant createTenant(
            @RequestParam String tenantName,
            @RequestParam String description) {

        return tenantService.createTenant(
                tenantName,
                description);
    }

    @GetMapping
    public List<Tenant> getAllTenants() {

        return tenantService.getAllTenants();
    }
}