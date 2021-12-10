package uz.jl.ui;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.mapper.BranchMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.branch.BranchService;
import uz.jl.services.employee.EmployeeService;
import uz.jl.utils.Input;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class EmployeeUI {
    static EmployeeService service = EmployeeService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());


    public static void create()  {
        String userName = getStr("Username-> ");
        String password = getStr("password-> ");
        String phoneNumber = getStr("Phone Number->");
        ResponseEntity<String> response = service.create(userName,password,phoneNumber);
        showResponse(response);
    }

    public static void delete() {
        String username=getStr("Username-> ");
        ResponseEntity<String> response = service.delete(username);

    }

    public static void list() {

    }

    public static void block() {

    }

    public static void unBlock() {

    }

    public static void blockList() {

    }
}
