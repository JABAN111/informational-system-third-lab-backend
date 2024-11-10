package is.fistlab.controllers;

import is.fistlab.dto.JwtAuthenticationResponse;
import is.fistlab.dto.UserDto;
import is.fistlab.security.sevices.Impl.AuthServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthorizationController {
    private final AuthServiceImpl authenticationService;

    @GetMapping("/hello")
    @CrossOrigin
    public String hello() {
        log.info("hello");
        return "hi";
    }

    @PostMapping("/create-user")
    public ResponseEntity<Response<JwtAuthenticationResponse>> registration(@RequestBody UserDto dto){
        return ResponseEntity.ok(new Response<>(authenticationService.signUp(dto)));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<JwtAuthenticationResponse>> login(@RequestBody UserDto dto){
        return ResponseEntity.ok(new Response<>(authenticationService.signIn(dto)));
    }
}
