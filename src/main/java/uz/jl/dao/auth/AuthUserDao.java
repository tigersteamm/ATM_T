package uz.jl.dao.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.auth.AuthUser;

import java.sql.DriverManager;
import java.util.Objects;

/**
 * @author Elmurodov Javohir, Thu 8:50 AM. 12/9/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDao extends BaseDao<AuthUser> {
    FRWAuthUser frwAuthUser = FRWAuthUser.getInstance();

    private static AuthUserDao dao;

    public static AuthUserDao getInstance() {
        if (Objects.isNull(dao))
            dao = new AuthUserDao();
        return dao;
    }

    public AuthUser findByUserName(String username) throws APIException {
        for (AuthUser user : frwAuthUser.getAll()) {
            if (user.getUsername().equals(username)) return user;
        }
        // TODO: 12/9/2021 translate please
        throw new APIException("User Not Found Exception", HttpStatus.HTTP_404);
    }
}
