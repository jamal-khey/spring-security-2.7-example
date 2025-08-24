package jwtexample.security.controllers;

import jwtexample.security.services.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jwtexample.repositories.UserRepository;
import jwtexample.dto.AuthentificationRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;


    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthentificationRequest authenticationRequest) {
        // 1. Retrieve the UserDetails first
        final UserDetails userDetails = userRepository.findUserByUserName(authenticationRequest.getUserName());

        // Check if the user exists
        if (userDetails == null) {
            return ResponseEntity.status(401).body("User not found.");
        }

        // 2. Perform authentication using the retrieved UserDetails
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(401).body("Incorrect username or password.");
        }

        // 3. If authentication is successful, generate and return the token
        Map<String, Object> extraClaims = new HashMap<>();
        final String jwtToken = jwtUtils.generateToken(extraClaims, userDetails);
        return ResponseEntity.ok(jwtToken);
    }

}
