package uz.jl.ui.menus;

import uz.jl.configs.Session;
import uz.jl.enums.auth.Role;
import uz.jl.utils.Print;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Elmurodov Javohir, Mon 11:30 AM. 12/6/2021
 */
public class Menu {
    public static Map<String, MenuKey> menus() {
        Role role = Session.getInstance().getUser().getRole();
        Map<String, MenuKey> menus = new LinkedHashMap<>();
        // TODO: 12/8/2021 do translations here
        if (Role.SUPER_ADMIN.equals(role)) {
            menus.put("Create Branch", MenuKey.CREATE_BRANCH);
            menus.put("Create Admin", MenuKey.CREATE_ADMIN);

            menus.put("Block Admin", MenuKey.BLOCK_ADMIN);
            menus.put("Block Branch", MenuKey.BLOCK_BRANCH);

            menus.put("Delete Admin", MenuKey.DELETE_ADMIN);
            menus.put("Delete Branch", MenuKey.DELETE_BRANCH);

            menus.put("Update Branch", MenuKey.UPDATE_BRANCH);
        } else if (Role.ADMIN.equals(role)) {
            menus.put("Create BRANCH", MenuKey.CREATE_BRANCH);
            menus.put("Delete BRANCH", MenuKey.DELETE_BRANCH);

            menus.put("Update BRANCH", MenuKey.UPDATE_BRANCH);
            menus.put("Create HR", MenuKey.CREATE_HR);

            menus.put("Delete HR", MenuKey.DELETE_HR);
            menus.put("Create ATM", MenuKey.CREATE_ATM);

            menus.put("Delete ATM", MenuKey.DELETE_ATM);
            menus.put("Block ATM", MenuKey.BLOCK_ATM);

            menus.put("Un Block ATM", MenuKey.UN_BLOCK_ATM);
        } else if (role.in(Role.ADMIN, Role.HR)) {
            menus.put("Employee List", MenuKey.LIST_EMPLOYEE);
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
            menus.put("Logout", MenuKey.LOGOUT);
        }
        menus.put("Quit", MenuKey.EXIT);
        return menus;
    }

    public static void show() {
        Menu.menus().forEach((k, v) -> Print.println(k + " -> " + v));
    }
}
