package is.fistlab.services.impl;

import is.fistlab.database.entities.User;
import is.fistlab.database.repositories.UserRepository;
import is.fistlab.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Value("${token.salt}")
    private String SALT;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User createNewUser(User user) {
        user.setPassword(user.getPassword());

        User newUser = userRepository.save(user);
        log.info("{} registered successfully",user.getUsername());

        return newUser;
    }
    @Transactional
    @Override
    public User updateUser(User user) {
        userRepository.getReferenceById(user.getId());
        //todo что блять здесь происходило
//        user.setPassword(PasswordProcessing.encryptPassword(user.getPassword(),SALT.getBytes()));
//        user.setPassword(user.getPassword());
        User newUser = userRepository.save(user);
        log.info("{} updated successfully",user.getUsername());
        return newUser;
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

    @Override
    public boolean isUserExists(String username) {
        User user = userRepository.findByUsername(username);
        if(Objects.nonNull(user)) {
            log.info("User with username: {} exist", username);
            return true;
        }
        log.info("User with username: {} not exist", username);
        return false;
    }

    @Override
    public User getUserByUsername(String username){
        if(!isUserExists(username)) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetailsService getUserDetailsService() {
        return this::getUserByUsername;
    }

    @Override
    public User getUser(User user) {
        if (!isUserExists(user.getUsername())){
            return null;//todo добавить кастомные ошибки
        }
        return userRepository.findByUsername(user.getUsername());
    }

}
