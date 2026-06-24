package com.uday.oauthsecuritysystem.controller;

import com.uday.oauthsecuritysystem.entity.User;
import com.uday.oauthsecuritysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/tenant/{tenantId}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<User> getUsersByTenant(
            @PathVariable Long tenantId) {

        return userRepository
                .findByTenantId(tenantId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public String deleteUser(
            @PathVariable Long id) {

        userRepository.deleteById(id);

        return "User Deleted";
    }
}