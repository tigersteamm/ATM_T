package uz.jl.configs;

import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.settings.Language;
import uz.jl.utils.BaseUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Elmurodov Javohir, Mon 12:21 PM. 11/29/2021
 */
public class AppConfig {
    public static Language language;

    private static final Properties properties = new Properties();

    public static void init() throws APIException {
        load();
//        initSuperUser();
        language = Language.getByCode(get("bank.default.language"));
    }

    private static void initSuperUser() {
        AuthUser user = new AuthUser();
        user.setId(BaseUtils.genId());
        user.setUsername(get("bank.super.username"));
        user.setPassword(get("bank.super.password"));
        user.setRole(Role.ADMIN);
        user.setLanguage(Language.getByCode(get("bank.default.language")));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedBy("-1");
        user.setPhoneNumber(get("bank.super.phone"));
        user.setCreatedBy(Session.getInstance().getUser().getId());
        FRWAuthUser.getInstance().writeAll(user);
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    private static void load() throws APIException {
        try {
            properties.load(new FileReader("src/main/resources/app.properties"));
        } catch (IOException e) {
            throw new APIException("File not found", HttpStatus.HTTP_404);
        }
    }
}
