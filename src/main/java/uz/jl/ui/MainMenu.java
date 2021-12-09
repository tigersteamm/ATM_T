package uz.jl.ui;

import uz.jl.configs.AppConfig;
import uz.jl.exceptions.APIException;
import uz.jl.models.auth.AuthUser;
import uz.jl.ui.menus.Menu;
import uz.jl.ui.menus.MenuKey;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

/**
 * @author Elmurodov Javohir, Wed 11:30 AM. 12/8/2021
 */
public class MainMenu {
    static {
        try {
            AppConfig.init();
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        Menu.show();
        String choice = Input.getStr("?:");
        MenuKey key = MenuKey.getByValue(choice);

        switch (key) {
            case LOGIN -> AuthUI.login();
            case REGISTER -> AuthUI.register();
            case PROFILE -> AuthUI.profile();
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
            case UN_BLOCK_HR -> AdminUI.unBlock();
            case BLOCK_LIST_HR -> AdminUI.blockList();

            case CREATE_USER -> HrUI.create();
            case DELETE_USER -> HrUI.delete();
            case LIST_USER -> HrUI.list();
            case BLOCK_USER -> HrUI.block();
            case UN_BLOCK_USER -> HrUI.unBlock();
            case BLOCK_LIST_USER -> HrUI.blockList();

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

            case EXIT -> {
                Print.println(Color.YELLOW, "Good bye");
                return;
            }
            default -> // TODO: 12/8/2021 do translations here
                    Print.println(Color.RED, "Wrong Choice");
        }
        main(args);
    }
}
