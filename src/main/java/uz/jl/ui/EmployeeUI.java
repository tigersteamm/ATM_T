package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.employee.EmployeeService;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class EmployeeUI {
    static EmployeeService service = EmployeeService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void create() {
        String userName = getStr("Username-> ");
        String password = getStr("password-> ");
        String phoneNumber = getStr("Phone Number->");
        ResponseEntity<String> response = service.create(userName, password, phoneNumber);
        showResponse(response);
    }

    public static void delete() {
        String username = getStr("Username-> ");
        ResponseEntity<String> response = service.delete(username);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.block(userName);
        showResponse(response);
    }

    public static void unBlock() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.unblock(userName);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
