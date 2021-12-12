package uz.jl.ui;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.atm.AtmDao;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.atm.AtmService;
import uz.jl.utils.Input;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class AtmUI {
    static AtmService service = uz.jl.services.atm.AtmService.getInstance(AtmDao.getInstance(), ATMMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void create() {
        String name = getStr(LangConfig.get(language, "atm.name") + " >> ");
        ResponseEntity<String> response = service.create(name);
        showResponse(response);
    }

    public static void update() {
        String oldName = getStr(LangConfig.get(language, "old.atm.name") + " >> ");
        String newName = getStr(LangConfig.get(language, "new.atm.name") + " >> ");
        ResponseEntity<String> response = service.update(oldName, newName);
        showResponse(response);
    }

    public static void delete() {
        String name = Input.getStr(LangConfig.get(language, "atm.name") + " >> ");
        ResponseEntity<String> response = service.delete(name);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String name = getStr(LangConfig.get(language, "atm.name") + " >> ");
        ResponseEntity<String> response = service.block(name);
        showResponse(response);
    }

    public static void unblock() {
        String name = getStr(LangConfig.get(language, "atm.name") + " >> ");
        ResponseEntity<String> response = service.unblock(name);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
