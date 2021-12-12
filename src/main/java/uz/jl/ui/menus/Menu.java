package uz.jl.ui.menus;

import uz.jl.configs.AppConfig;
import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.enums.auth.Role;
import uz.jl.models.settings.Language;
import uz.jl.utils.Print;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Elmurodov Javohir, Mon 11:30 AM. 12/6/2021
 */
public class Menu {
    public static Map<String, MenuKey> menus() {
        Role role = Session.getInstance().getUser().getRole();
        Language language = Session.getInstance().getUser().getLanguage();

        Map<String, MenuKey> menus = new LinkedHashMap<>();
        // TODO: 12/8/2021 do translations here
        if (Role.SUPER_ADMIN.equals(role)) {
            menus.put(LangConfig.get(language, "branch.create"), MenuKey.CREATE_BRANCH);
            menus.put(LangConfig.get(language, "admin.create"), MenuKey.CREATE_ADMIN);

            menus.put(LangConfig.get(language, "admin.delete"), MenuKey.DELETE_ADMIN);
            menus.put(LangConfig.get(language, "branch.delete"), MenuKey.DELETE_BRANCH);

            menus.put(LangConfig.get(language, "admin.block"), MenuKey.BLOCK_ADMIN);
            menus.put(LangConfig.get(language, "branch.block"), MenuKey.BLOCK_BRANCH);

            menus.put(LangConfig.get(language, "admin.unblock"), MenuKey.UN_BLOCK_ADMIN);
            menus.put(LangConfig.get(language, "branch.unblock"), MenuKey.UN_BLOCK_BRANCH);

            menus.put("List Admin", MenuKey.LIST_ADMIN);
            menus.put("List Branch", MenuKey.LIST_BRANCH);

            menus.put("Update Branch", MenuKey.UPDATE_BRANCH);
        } else if (Role.ADMIN.equals(role)) {
            menus.put("Create BRANCH", MenuKey.CREATE_BRANCH);
            menus.put("Create HR", MenuKey.CREATE_HR);
            menus.put("Create ATM", MenuKey.CREATE_ATM);
            menus.put("Create Employee", MenuKey.CREATE_EMPLOYEE);

            menus.put("Delete BRANCH", MenuKey.DELETE_BRANCH);
            menus.put("Delete HR", MenuKey.DELETE_HR);
            menus.put("Delete ATM", MenuKey.DELETE_ATM);
            menus.put("Delete Employee", MenuKey.DELETE_EMPLOYEE);

            menus.put("Block BRANCH", MenuKey.BLOCK_BRANCH);
            menus.put("Block HR", MenuKey.BLOCK_HR);
            menus.put("Block ATM", MenuKey.BLOCK_ATM);
            menus.put("Block Employee", MenuKey.BLOCK_EMPLOYEE);

            menus.put("UnBlock BRANCH", MenuKey.UN_BLOCK_BRANCH);
            menus.put("UnBlock HR", MenuKey.UN_BLOCK_HR);
            menus.put("UnBlock ATM", MenuKey.UN_BLOCK_ATM);
            menus.put("UnBlock Employee", MenuKey.UN_BLOCK_EMPLOYEE);

            menus.put("List BRANCH", MenuKey.LIST_BRANCH);
            menus.put("List HR", MenuKey.LIST_HR);
            menus.put("List ATM", MenuKey.LIST_ATM);
            menus.put("List Employee", MenuKey.LIST_EMPLOYEE);

            menus.put("Update BRANCH", MenuKey.UPDATE_BRANCH);
        } else if (role.in(Role.ADMIN, Role.HR)) {
            menus.put("List Employee", MenuKey.LIST_EMPLOYEE);
            menus.put("Create Employee", MenuKey.CREATE_EMPLOYEE);

            menus.put("Delete Employee", MenuKey.DELETE_EMPLOYEE);
            menus.put("Block Employee", MenuKey.BLOCK_EMPLOYEE);

            menus.put("Un block Employee", MenuKey.UN_BLOCK_EMPLOYEE);
            menus.put("Blocked Employee List", MenuKey.BLOCK_LIST_EMPLOYEE);
        } else if (Role.EMPLOYEE.equals(role)) {
            menus.put("Blocked Employee List", MenuKey.BLOCK_LIST_ATM);
            menus.put("Update Atm", MenuKey.UPDATE_ATM);

            menus.put("Atm List", MenuKey.LIST_ATM);
        } else if (Role.ANONYMOUS.equals(role)) {
            menus.put("Login", MenuKey.LOGIN);
        }
        if (!Role.ANONYMOUS.equals(role)) {
            menus.put("Change language", MenuKey.CHANGE_LANG);
            menus.put("Logout", MenuKey.LOGOUT);
        }
        menus.put("Quit", MenuKey.EXIT);
        return menus;
    }

    public static void show() {
        Menu.menus().forEach((k, v) -> Print.println(k + " -> " + v));
    }
}
