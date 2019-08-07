package com.simkiv.tickets.service;

import com.simkiv.tickets.dto.JwtAuthenticationResponse;
import com.simkiv.tickets.dto.SignInRequest;
import com.simkiv.tickets.dto.SignUpRequest;
import com.simkiv.tickets.exception.ConflictException;
import com.simkiv.tickets.model.User;
import com.simkiv.tickets.repository.UserRepository;
import com.simkiv.tickets.security.JwtTokenProvider;
import com.simkiv.tickets.security.UserPrincipal;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    @Value("${stripe.keys.secret}")
    private String stripeSecretKey;

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public JwtAuthenticationResponse authenticateUser(SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("User with [email: {}] has logged in", userPrincipal.getEmail());

        return new JwtAuthenticationResponse(jwt);
    }

    public Long createUser(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Email [email: " + signUpRequest.getEmail() + "] is already taken");
        }

        User user = new User(signUpRequest.getEmail(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("Successfully registered user with [email: {}]", user.getEmail());

        Optional<Customer> customer = createCustomer(signUpRequest);

        if (customer.isPresent()) {
            user.setStripeId(customer.get().getId());
        } else {
            log.info("Stripe customer was not created");
        }

        User userFromDb = userRepository.save(user);
        log.info(userFromDb.getStripeId());
        return userFromDb.getId();
    }

    /**
     * method for creation customer of stripe.com
     */
    private Optional<Customer> createCustomer(SignUpRequest signUpRequest) {
        Stripe.apiKey = stripeSecretKey;

        Map<String, Object> customerParams = new HashMap<>();

        customerParams.put("description", "Customer for " + signUpRequest.getEmail());
        customerParams.put("email", signUpRequest.getEmail());
        customerParams.put("name", signUpRequest.getFirstName() + " " + signUpRequest.getLastName());

        try {
            return Optional.ofNullable(Customer.create(customerParams));
        } catch (StripeException e) {
            log.error(e.getMessage() + "Problem with stripe customer creation");
        }
        return Optional.empty();
    }
}
