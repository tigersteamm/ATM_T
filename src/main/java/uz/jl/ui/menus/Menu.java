package uz.jl.ui.menus;

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

            menus.put(LangConfig.get(language, "admin.list"), MenuKey.LIST_ADMIN);
            menus.put(LangConfig.get(language, "branch.list"), MenuKey.LIST_BRANCH);

            menus.put(LangConfig.get(language, "branch.update"), MenuKey.UPDATE_BRANCH);
        } else if (Role.ADMIN.equals(role)) {
            menus.put(LangConfig.get(language, "branch.create"), MenuKey.CREATE_BRANCH);
            menus.put(LangConfig.get(language, "hr.create"), MenuKey.CREATE_HR);
            menus.put(LangConfig.get(language, "atm.create"), MenuKey.CREATE_ATM);
            menus.put(LangConfig.get(language, "employee.create"), MenuKey.CREATE_EMPLOYEE);

            menus.put(LangConfig.get(language, "branch.delete"), MenuKey.DELETE_BRANCH);
            menus.put(LangConfig.get(language, "hr.delete"), MenuKey.DELETE_HR);
            menus.put(LangConfig.get(language, "atm.delete"), MenuKey.DELETE_ATM);
            menus.put(LangConfig.get(language, "employee.delete"), MenuKey.DELETE_EMPLOYEE);

            menus.put(LangConfig.get(language, "branch.block"), MenuKey.BLOCK_BRANCH);
            menus.put(LangConfig.get(language, "hr.block"), MenuKey.BLOCK_HR);
            menus.put(LangConfig.get(language, "atm.block"), MenuKey.BLOCK_ATM);
            menus.put(LangConfig.get(language, "employee.block"), MenuKey.BLOCK_EMPLOYEE);

            menus.put(LangConfig.get(language, "branch.unblock"), MenuKey.UN_BLOCK_BRANCH);
            menus.put(LangConfig.get(language, "hr.unblock"), MenuKey.UN_BLOCK_HR);
            menus.put(LangConfig.get(language, "atm.unblock"), MenuKey.UN_BLOCK_ATM);
            menus.put(LangConfig.get(language, "employee.unblock"), MenuKey.UN_BLOCK_EMPLOYEE);

            menus.put(LangConfig.get(language, "branch.list"), MenuKey.LIST_BRANCH);
            menus.put(LangConfig.get(language, "hr.list"), MenuKey.LIST_HR);
            menus.put(LangConfig.get(language, "atm.list"), MenuKey.LIST_ATM);
            menus.put(LangConfig.get(language, "employee.list"), MenuKey.LIST_EMPLOYEE);

            menus.put(LangConfig.get(language, "branch.update"), MenuKey.UPDATE_BRANCH);
        } else if (role.in(Role.ADMIN, Role.HR)) {
            menus.put(LangConfig.get(language, "employee.list"), MenuKey.LIST_EMPLOYEE);
            menus.put(LangConfig.get(language, "employee.create"), MenuKey.CREATE_EMPLOYEE);

            menus.put(LangConfig.get(language, "employee.delete"), MenuKey.DELETE_EMPLOYEE);
            menus.put(LangConfig.get(language, "employee.block"), MenuKey.BLOCK_EMPLOYEE);

            menus.put(LangConfig.get(language, "employee.unblock"), MenuKey.UN_BLOCK_EMPLOYEE);
            menus.put(LangConfig.get(language, "employee.list.blocked"), MenuKey.BLOCK_LIST_EMPLOYEE);
        } else if (Role.EMPLOYEE.equals(role)) {
            menus.put(LangConfig.get(language, "atm.update"), MenuKey.UPDATE_ATM);
            menus.put(LangConfig.get(language, "atm.list"), MenuKey.LIST_ATM);
            menus.put(LangConfig.get(language, "client.create"), MenuKey.CREATE_USER);
            menus.put(LangConfig.get(language, "client.delete"), MenuKey.DELETE_USER);
            menus.put(LangConfig.get(language, "client.block"), MenuKey.BLOCK_USER);
            menus.put(LangConfig.get(language, "client.unblock"), MenuKey.UN_BLOCK_USER);
            menus.put(LangConfig.get(language, "client.list"), MenuKey.LIST_USER);
        } else if (Role.CLIENT.equals(role)) {
            menus.put(LangConfig.get(language, "atmProcess.useAtm"), MenuKey.USE_ATM);
        } else if (Role.ANONYMOUS.equals(role)) {
            menus.put(LangConfig.get(language, "login"), MenuKey.LOGIN);
        }
        if (!Role.ANONYMOUS.equals(role)) {
            menus.put(LangConfig.get(language, "change.lang"), MenuKey.CHANGE_LANG);
            menus.put(LangConfig.get(language, "logout"), MenuKey.LOGOUT);
        }
        menus.put(LangConfig.get(language, "quit"), MenuKey.EXIT);
        return menus;
    }

    public static void show() {
        Menu.menus().forEach((k, v) -> Print.println(k + " >> " + v));
    }
}
