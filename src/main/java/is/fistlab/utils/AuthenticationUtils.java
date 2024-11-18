package is.fistlab.utils;

import is.fistlab.database.entities.CreatorAware;
import is.fistlab.database.entities.User;
import is.fistlab.database.enums.UserRole;
import is.fistlab.exceptions.auth.NotEnoughRights;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationUtils {


    /**
     * Проверяет, является ли текущий пользователь создателем объекта
     *
     * @throws NotEnoughRights в случае, если пользователь не имеет достаточное количество прав и не является админом
     */
    public void verifyAccess(CreatorAware creatorAware) {
        var user = getCurrentUserFromContext();

        if (Objects.isNull(user.getRole()) || user.getRole() != UserRole.ROLE_ADMIN) {
            throw new NotEnoughRights("Только создатель или админ может удалять/редактировать объекты");
        }
    }

    public User getCurrentUserFromContext(){
        var user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (Objects.nonNull(user)) {
            return user;
        }

        throw new RuntimeException("Контекст не нашел текущего пользователя");
    }
}