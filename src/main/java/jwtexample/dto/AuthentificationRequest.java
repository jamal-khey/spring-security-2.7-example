package jwtexample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthentificationRequest {
    @NotBlank(message = "can't be empty")
    String userName;
    @NotBlank(message = "can't be empty")
    String password;
}
