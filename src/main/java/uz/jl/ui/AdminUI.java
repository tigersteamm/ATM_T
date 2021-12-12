package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.hr.HRService;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:10 PM. 12/8/2021
 */
public class AdminUI {
    static HRService service = HRService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void create() {
        String userName = getStr(LangConfig.get(language, "username"));
        String password = getStr(LangConfig.get(language, "password") + " >> ");
        ResponseEntity<String> response = service.create(userName, password);
        showResponse(response);
    }

    public static void delete() {
        String userName = getStr(LangConfig.get(language, "username"));
        ResponseEntity<String> response = service.delete(userName);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String userName = getStr(LangConfig.get(language, "username"));
        ResponseEntity<String> response = service.block(userName);
        showResponse(response);
    }

    public static void unblock() {
        String userName = getStr(LangConfig.get(language, "username"));
        ResponseEntity<String> response = service.unblock(userName);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
