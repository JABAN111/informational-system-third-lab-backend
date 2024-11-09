package is.fistlab.services.impl;

import is.fistlab.database.entities.PotentialAdmin;
import is.fistlab.database.entities.User;
import is.fistlab.database.enums.UserRole;
import is.fistlab.database.repositories.PotentialAdminRepository;
import is.fistlab.database.repositories.UserRepository;
import is.fistlab.services.AdminProcessingService;
import is.fistlab.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdminProcessingServiceImpl implements AdminProcessingService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PotentialAdminRepository adminRepository;

    @Override
    public boolean isAnyAdminExist() {
        return !adminRepository.findAll().isEmpty();
    }

    @Override
    public List<PotentialAdmin> getAllPotentialAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void addUserToWaitingList(User user) {
        if(!isAnyAdminExist()){
            PotentialAdmin admin = new PotentialAdmin();
            admin.setUser(user);
            admin.setWantedRole(UserRole.ROLE_ADMIN);
            adminRepository.save(admin);
        }
        user.setRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
    }

    @Override
    public void removeUserFromWaitingList(User user) {

    }

    @Override
    public void giveAdminRoleToUser(User user) {

    }

}
