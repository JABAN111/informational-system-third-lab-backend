package is.fistlab.controllers;

import is.fistlab.database.entities.User;
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
    public Response<JwtAuthenticationResponse> registration(@RequestBody UserDto dto){
        return new Response<>(authenticationService.signUp(dto));
    }

    @PostMapping("/login")
    public Response<JwtAuthenticationResponse> login(@RequestBody UserDto dto){
        return new Response<>(authenticationService.signIn(dto));
    }
}
