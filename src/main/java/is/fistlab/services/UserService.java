package is.fistlab.services;

import is.fistlab.database.entities.User;

public interface UserService {
    User createNewUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User getUser(Long id);
}