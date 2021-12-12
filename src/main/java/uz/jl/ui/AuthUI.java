package uz.jl.ui;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AuthService;
import uz.jl.utils.Input;

/**
 * @author Elmurodov Javohir, Wed 12:08 PM. 12/8/2021
 */
public class AuthUI extends BaseUI {
    static AuthService service = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void login() {
        String username = Input.getStr(LangConfig.get(language, "username") + " >> ");
        String password = Input.getStr(LangConfig.get(language, "password") + " >> ");
        ResponseEntity<String> response = service.login(username, password);
        showResponse(response);
    }

    public static void profile() {
        ResponseEntity<String> response = service.profile();
        showResponse(response);
    }

    public static void logout() {
        ResponseEntity<String> response = service.logout();
        showResponse(response);
    }

    public static void changeLang() {
        Language.showAll();
        String lang = Input.getStr(LangConfig.get(language, "language") + " >> ");
        ResponseEntity<String> response = service.changeLang(lang);
        showResponse(response);
    }
}
