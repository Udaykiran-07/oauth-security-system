package com.uday.oauthsecuritysystem.security;

import com.uday.oauthsecuritysystem.entity.User;
import com.uday.oauthsecuritysystem.exception.ResourceNotFoundException;
import com.uday.oauthsecuritysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
            String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return new CustomUserDetails(user);
    }
}