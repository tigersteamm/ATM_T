package uz.jl.configs;

import lombok.Getter;
import lombok.Setter;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.settings.Language;

import java.util.Objects;

/**
 * @author Elmurodov Javohir, Mon 11:39 AM. 12/6/2021
 */
public class Session {
    @Getter
    @Setter
    private AuthUser user;
    private static Session session;

    private Session() {
        user = new AuthUser();
    }

    public static Session getInstance() {
        if (Objects.isNull(session)) {
            session = new Session();
        }
        return session;
    }

    @Override
    public String toString() {
        return
                "username=" + user.getUsername() +
//                        "\npassword=" + user.getPassword() +
                        "\nrole=" + user.getRole() +
                        "\nstatus=" + user.getStatus() +
                        "\nphone number=" + user.getPhoneNumber() +
                        "\nlanguage=" + user.getLanguage().toString()
                ;
    }
}
