package jwtexample;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Home", description = "Home API endpoints")
@SecurityRequirement(name = "bearerAuth")
public class HomeController {

  @Operation(summary = "Get home message", description = "Returns a simple home message")
  @GetMapping
  public ResponseEntity<String> home() {
    return ResponseEntity.ok("home");
  }

}
