package is.fistlab.services.impl;

import is.fistlab.database.entities.User;
import is.fistlab.database.repositories.UserRepository;
import is.fistlab.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void registerUser(User user) {
        userRepository.save(user);
        log.info("{} registered successfully",user.getUsername());
    }
    @Transactional
    @Override
    public void updateUser(User user) {
        userRepository.getReferenceById(user.getId());
        userRepository.save(user);
        log.info("{} updated successfully",user.getUsername());
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("User with id: {} deleted successfully",id);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            log.info("User with id: {} found", id);
            return user.get();
        }
        log.error("User with id: {} not found", id);
        return null;
    }
}
