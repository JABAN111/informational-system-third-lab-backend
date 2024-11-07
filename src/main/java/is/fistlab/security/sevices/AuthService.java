package is.fistlab.security.sevices;

import is.fistlab.dto.JwtAuthenticationResponse;
import is.fistlab.dto.UserDto;

public interface AuthService {
    JwtAuthenticationResponse signUp(UserDto requestDto);
    JwtAuthenticationResponse signIn(UserDto request);
}
