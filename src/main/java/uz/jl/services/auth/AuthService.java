package uz.jl.services.auth;

import uz.jl.configs.AppConfig;
import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.Objects;

/**
 * @author Elmurodov Javohir, Mon 10:46 AM. 12/6/2021
 */
public class AuthService
        extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {

    private static AuthService service;

    public static AuthService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AuthService(repository, mapper);
        }
        return service;
    }

    protected AuthService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    Role role = Session.getInstance().getUser().getRole();

    public ResponseEntity<String> login(String username, String password) {
        if (!Role.ANONYMOUS.equals(role)) {
            return new ResponseEntity<>(LangConfig.get(language, "forbidden"), HttpStatus.HTTP_403);
        }
        try {
            AuthUser user = repository.findByUserName(username);
            if (user.getDeleted() == 1) {
                return new ResponseEntity<>("Not Found", HttpStatus.HTTP_404);
            }
            if (Objects.isNull(user) || !user.getPassword().equals(password)) {
                return new ResponseEntity<>("Bad Credentials", HttpStatus.HTTP_400);
            }
            if (user.getStatus().equals(UserStatus.BLOCKED)) {
                return new ResponseEntity<>(LangConfig.get(language, "forbidden"), HttpStatus.HTTP_403);
            }
            Session.getInstance().setUser(user);
            return new ResponseEntity<>("success", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }


    public ResponseEntity<String> logout() {
        if (!Role.ANONYMOUS.equals(role)) {
            return new ResponseEntity<>(LangConfig.get(language, "forbidden"), HttpStatus.HTTP_403);
        }
        Session.getInstance().setUser(new AuthUser());
        return new ResponseEntity<>("success", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> profile() {
        if (!Role.ANONYMOUS.equals(role)) {
            return new ResponseEntity<>(LangConfig.get(language, "forbidden"), HttpStatus.HTTP_403);
        }
        Print.println(Color.GREEN, Session.getInstance().getUser());
        return new ResponseEntity<>("success", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> changeLang(String lang) {
        switch (lang.toUpperCase()) {
            case "UZ", "RU", "EN" -> {
                Session.getInstance().getUser().setLanguage(
                        switch (lang.toUpperCase()) {
                            case "UZ" -> Language.UZ;
                            case "RU" -> Language.RU;
                            default -> Language.EN;
                        }
                );
                FRWAuthUser.getInstance().writeAll(FRWAuthUser.getInstance().getAll());
                return new ResponseEntity<>("success", HttpStatus.HTTP_200);
            }
            default -> {
                return new ResponseEntity<>("bad choice", HttpStatus.HTTP_406);
            }
        }
    }
}
