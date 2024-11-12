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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AdminProcessingServiceImpl implements AdminProcessingService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PotentialAdminRepository adminRepository;

    @Override
    public boolean isAnyAdminExist(){
        return !userRepository.findAllByRole(UserRole.ROLE_ADMIN).isEmpty();
    }

    public List<PotentialAdmin> getAllPotentialAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Page<PotentialAdmin> getPotentialAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public void addUserToWaitingList(User user) {
        PotentialAdmin admin = new PotentialAdmin();
        admin.setUser(user);
        admin.setWantedRole(UserRole.ROLE_ADMIN);
        adminRepository.save(admin);
    }

    @Override
    public void removeUserFromWaitingList(User user) {
        assert user != null;
        adminRepository.removeByUser(user);
        log.info("user: {} deleted", user);
    }

    @Transactional
    @Override
    public void rejectUserForAdminRole(User user) {
        assert user != null;
        adminRepository.removeByUser(user);
        log.info("user: {} rejected admin role", user);
    }
    /**
     *
     * @param id - ид в очереди потенциальных админов, НЕ ИД ПОЛЬЗОВАТЕЛЯ
     * @param user
     * @return
     */
    @Override
    @Transactional
    public User giveAdminRoleToUser(Long id, User user) {
        //строка из таблицы с админами
        var potentialAdmin = adminRepository.findById(id);

        if(potentialAdmin.isEmpty()){
            log.error("Potential admin with id {} does not exist", id);
            //fixme
            throw new RuntimeException("Potential admin with id " + id + " does not exist");
        }

        PotentialAdmin admin = potentialAdmin.get();
        var wantAdminRole = admin.getUser();
        var userFromRepThatWantedAdminRole = userRepository.findByUsername(wantAdminRole.getUsername());
        userFromRepThatWantedAdminRole.setRole(UserRole.ROLE_ADMIN);

        System.out.println(userFromRepThatWantedAdminRole);
        System.out.println(admin);
//        wantAdminRole.setRole(UserRole.ROLE_ADMIN);
        userRepository.save(userFromRepThatWantedAdminRole);
        removeUserFromWaitingList(user);
        return wantAdminRole;
    }


    @Override
    public boolean isPotentialAdminExist(Long id){
        return adminRepository.findById(id).isPresent();
    }

//    @Override
//    public boolean isPotentialAdminExist(Long id){
//        return adminRepository.findById(id).isPresent();
//    }

}
