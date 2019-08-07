package com.simkiv.tickets.service;

import com.simkiv.tickets.dto.UserSummary;
import com.simkiv.tickets.security.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .build();
    }
}
