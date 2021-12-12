package uz.jl;

import uz.jl.configs.AppConfig;
import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.atm.AtmDao;
import uz.jl.dao.card.CardDao;
import uz.jl.enums.auth.Role;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.settings.Language;
import uz.jl.services.atm.AtmService;
import uz.jl.ui.*;
import uz.jl.ui.menus.Menu;
import uz.jl.ui.menus.MenuKey;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.Objects;

import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 11:30 AM. 12/8/2021
 */
public class App {
    static {
        try {
            AppConfig.init();
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Language language = Session.getInstance().getUser().getLanguage();

        if (Session.getInstance().getUser().getRole().equals(Role.CLIENT)) {
            while (Objects.isNull(Session.getInstance().getAtm())) {
                AtmService.getInstance(AtmDao.getInstance(), ATMMapper.getInstance()).unblockList();
                String name = getStr(">>>>");
                try {
                    Session.getInstance().setAtm(AtmDao.findByName(name));
                } catch (APIException e) {
                    Print.println("Enter valid ATM name");
                }
            }
            while (Objects.isNull(Session.getInstance().getCard())) {
                CardDao.getInstance().ShowByHolderId(Session.getInstance().getUser().getId());
                String pan = getStr("Pan >>>>");
                try {
                    Session.getInstance().setCard(CardDao.getInstance().findByPan(pan));
                } catch (APIException e) {
                    Print.println("Enter valid pan");
                }
            }
        }

        Menu.show();
        String choice = getStr(">>>>");
        MenuKey key = MenuKey.getByValue(choice);

        switch (key) {
            case LOGIN -> AuthUI.login();
            case PROFILE -> AuthUI.profile();
            case CHANGE_LANG -> AuthUI.changeLang();
            case LOGOUT -> AuthUI.logout();

            case CREATE_ADMIN -> SuperAdminUI.create();
            case DELETE_ADMIN -> SuperAdminUI.delete();
            case LIST_ADMIN -> SuperAdminUI.list();
            case BLOCK_ADMIN -> SuperAdminUI.block();
            case UN_BLOCK_ADMIN -> SuperAdminUI.unblock();
            case BLOCK_LIST_ADMIN -> SuperAdminUI.blockList();

            case CREATE_HR -> AdminUI.create();
            case DELETE_HR -> AdminUI.delete();
            case LIST_HR -> AdminUI.list();
            case BLOCK_HR -> AdminUI.block();
            case UN_BLOCK_HR -> AdminUI.unblock();
            case BLOCK_LIST_HR -> AdminUI.blockList();

            case CREATE_USER -> ClientUI.create();
            case DELETE_USER -> ClientUI.delete();
            case LIST_USER -> ClientUI.list();
            case BLOCK_USER -> ClientUI.block();
            case UN_BLOCK_USER -> ClientUI.unBlock();
            case BLOCK_LIST_USER -> ClientUI.blockList();
            case GIVE_CARD -> ClientUI.giveCard();

            case CREATE_EMPLOYEE -> EmployeeUI.create();
            case DELETE_EMPLOYEE -> EmployeeUI.delete();
            case LIST_EMPLOYEE -> EmployeeUI.list();
            case BLOCK_EMPLOYEE -> EmployeeUI.block();
            case UN_BLOCK_EMPLOYEE -> EmployeeUI.unBlock();
            case BLOCK_LIST_EMPLOYEE -> EmployeeUI.blockList();

            case CREATE_BRANCH -> BranchUI.create();
            case UPDATE_BRANCH -> BranchUI.update();
            case DELETE_BRANCH -> BranchUI.delete();
            case LIST_BRANCH -> BranchUI.list();
            case BLOCK_BRANCH -> BranchUI.block();
            case UN_BLOCK_BRANCH -> BranchUI.unblock();
            case BLOCK_LIST_BRANCH -> BranchUI.blockList();

            case CREATE_ATM -> AtmUI.create();
            case UPDATE_ATM -> AtmUI.update();
            case DELETE_ATM -> AtmUI.delete();
            case LIST_ATM -> AtmUI.list();
            case BLOCK_ATM -> AtmUI.block();
            case UN_BLOCK_ATM -> AtmUI.unblock();
            case BLOCK_LIST_ATM -> AtmUI.blockList();

//            case SMS_INFO -> AtmProcessUI.smsInfo();
            case CHANGE_PIN -> AtmProcessUI.ChangePin();
//            case CARD_INFO -> AtmProcessUI.infoCard();
            case CARD_BALANCE -> AtmProcessUI.cardBalance();
            case CASH_WITHDRAWAL -> AtmProcessUI.cashWithdrawal();
//            case CARD_DEPOSIT -> AtmProcessUI.cardDeposit();
            case RETURN_CARD -> {
                AtmProcessUI.returnCard();
                AuthUI.logout();
            }

            case EXIT -> {
                Print.println(Color.YELLOW, LangConfig.get(language, "bye"));
                return;
            }
            default -> Print.println(Color.RED, LangConfig.get(language, "wrong.choice"));
        }
        main(args);
    }
}
