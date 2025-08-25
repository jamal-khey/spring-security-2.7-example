package jwtexample.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Schema(description = "Authentication request model")
public class AuthentificationRequest {
    @Schema(description = "Username for authentication", example = "user123", required = true)
    @NotBlank(message = "can't be empty")
    String userName;
    
    @Schema(description = "Password for authentication", example = "password123", required = true)
    @NotBlank(message = "can't be empty")
    String password;
}
