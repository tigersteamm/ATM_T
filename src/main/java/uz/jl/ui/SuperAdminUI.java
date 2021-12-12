package uz.jl.ui;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.mapper.BranchMapper;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.admin.AdminService;
import uz.jl.services.branch.BranchService;
import uz.jl.utils.Print;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Color.GREEN;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:09 PM. 12/8/2021
 */
public class SuperAdminUI {
    static AdminService service = AdminService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        BranchService serviceBranch = BranchService.getInstance(BranchDao.getInstance(), BranchMapper.getInstance());
        serviceBranch.list();
        String branchName = getStr("Choose branch : ");
        Branch branch = BranchDao.getInstance().findByNameWithNull(branchName);


        Print.println(GREEN, "Create an admin for the branch");
        String userName = getStr("Username = ");
        String password = getStr("password = ");
        ResponseEntity<String> response = service.create(userName, password, branch);
        showResponse(response);
    }

    public static void delete() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.delete(userName);
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

    public static void unblock() {
        String userName = getStr("Username = ");
        ResponseEntity<String> response = service.unblock(userName);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
