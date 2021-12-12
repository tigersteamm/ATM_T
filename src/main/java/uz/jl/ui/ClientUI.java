package uz.jl.ui;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.client.ClientService;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author D4uranbek сб. 9:40. 11.12.2021
 */
public class ClientUI {

    static ClientService service = ClientService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void create() {
        String userName = getStr(LangConfig.get(language, "username") + " >> ");
        String password = getStr(LangConfig.get(language, "password") + " >> ");
        String phoneNumber = getStr(LangConfig.get(language, "phone.number") + " >> ");

        String type = getStr(LangConfig.get(language, "card.type") + " >> ");
        String pin = getStr(LangConfig.get(language, "password") + " >> ");
        ResponseEntity<String> response = service.create(userName, password, phoneNumber, type, pin);
        showResponse(response);
    }

    public static void delete() {
        String username = getStr(LangConfig.get(language, "username") + " >> ");
        ResponseEntity<String> response = service.delete(username);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String userName = getStr(LangConfig.get(language, "username") + " >> ");
        ResponseEntity<String> response = service.block(userName);
        showResponse(response);
    }

    public static void unBlock() {
        String userName = getStr(LangConfig.get(language, "username") + " >> ");
        ResponseEntity<String> response = service.unblock(userName);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }

    public static void giveCard() {
        String holderUsername = getStr(LangConfig.get(language, "holder.username") + " >> ");
        String type = getStr(LangConfig.get(language, "card.type") + " >> ");
        String password = getStr(LangConfig.get(language, "password") + " >> ");

        ResponseEntity<String> response = service.giveCard(holderUsername, type, password);
        showResponse(response);
    }
}
