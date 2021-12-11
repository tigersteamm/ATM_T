package uz.jl.ui;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.atm.Status;
import uz.jl.enums.card.CardType;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.client.ClientService;

import java.math.BigDecimal;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author D4uranbek сб. 9:40. 11.12.2021
 */
public class ClientUI {

    static ClientService service = ClientService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String userName = getStr("Username: ");
        String password = getStr("password: ");
        String phoneNumber = getStr("Phone Number: ");
        ResponseEntity<String> response = service.create(userName, password, phoneNumber);
        showResponse(response);
    }

    public static void delete() {
        String username = getStr("Username: ");
        ResponseEntity<String> response = service.delete(username);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String userName = getStr("Username: ");
        ResponseEntity<String> response = service.block(userName);
        showResponse(response);
    }

    public static void unBlock() {
        String userName = getStr("Username: ");
        ResponseEntity<String> response = service.unblock(userName);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }

    public static void giveCard() {
        String holderUsername = getStr("Holder username: ");
        String type = getStr("Card type: ");
        String password = getStr("Password: ");

        ResponseEntity<String> response = service.giveCard(holderUsername, type, password);
        showResponse(response);
    }
}
