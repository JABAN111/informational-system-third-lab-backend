package is.fistlab.controllers;

import is.fistlab.database.enums.UserRole;
import is.fistlab.dto.JwtAuthenticationResponse;
import is.fistlab.dto.UserDto;
import is.fistlab.security.sevices.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthorizationController {
    private final AuthService authenticationService;

    @PostMapping("/create-user")
    public ResponseEntity<Response<JwtAuthenticationResponse>> registration(@RequestBody UserDto dto) {
        return ResponseEntity.ok(new Response<>(authenticationService.signUp(dto)));
    }

    @GetMapping("/is-token-valid")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> isTokenValid() {
        return ResponseEntity.ok("");
    }

    @GetMapping("/get-role")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Response<UserRole>> getUserRole() {
        return ResponseEntity.ok(
                new Response<>(authenticationService.getUserRole())
        );
    }

    @GetMapping("/get-username")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Response<String>> getUsername() {
        return ResponseEntity.ok(
                new Response<>(null, authenticationService.getUsername())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Response<JwtAuthenticationResponse>> login(@RequestBody UserDto dto) {
        return ResponseEntity.ok(new Response<>(authenticationService.signIn(dto)));
    }
}
