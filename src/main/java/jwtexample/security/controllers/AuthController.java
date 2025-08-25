package jwtexample.security.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Operation(summary = "Authenticate user", description = "Authenticates a user with username and password and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful", 
                    content = @Content(schema = @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."))),
            @ApiResponse(responseCode = "401", description = "Authentication failed - User not found or incorrect credentials")
    })
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
