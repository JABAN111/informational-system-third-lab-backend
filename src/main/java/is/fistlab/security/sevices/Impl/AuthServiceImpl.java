package is.fistlab.security.sevices.Impl;

import is.fistlab.database.entities.User;
import is.fistlab.database.enums.UserRole;
import is.fistlab.dto.JwtAuthenticationResponse;
import is.fistlab.dto.UserDto;
import is.fistlab.security.sevices.AuthService;
import is.fistlab.security.sevices.JwtService;
import is.fistlab.services.AdminProcessingService;
import is.fistlab.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdminProcessingService adminProcessingService;

    public JwtAuthenticationResponse signUp(UserDto requestDto) {

        var user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(UserRole.ROLE_USER)
                .build();

        userService.createNewUser(user);

        setAdminOrAddToWaitList(requestDto, user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    private void setAdminOrAddToWaitList(UserDto requestedUser, User userSavedInRepository) {
        if(UserRole.valueOf(requestedUser.getRole()) == UserRole.ROLE_ADMIN){
            if(adminProcessingService.isAnyAdminExist()){
                userSavedInRepository.setRole(UserRole.ROLE_ADMIN);
                userService.updateUser(userSavedInRepository);
            }else{
                adminProcessingService.addUserToWaitingList(userSavedInRepository);
            }
        }
    }


    public JwtAuthenticationResponse signIn(UserDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .getUserDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}