package uz.jl.ui;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.atm.ATMType;
import uz.jl.enums.auth.Role;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.admin.AdminService;
import uz.jl.services.employee.EmployeeService;

import java.util.Locale;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:09 PM. 12/8/2021
 */
public class SuperAdminUI {
    static AdminService service = AdminService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String userName = getStr("Username-> ");
        String password = getStr("password-> ");
        ResponseEntity<String> response = service.create(userName, password);
        showResponse(response);

    }

    public static void delete() {

    }

    public static void list() {

    }

    public static void block() {

    }

    public static void unblock() {

    }

    public static void blockList() {

    }
}
