package uz.jl.ui;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.mapper.BranchMapper;
import uz.jl.models.branch.Branch;
import uz.jl.models.settings.Language;
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
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void create() {
        BranchService serviceBranch = BranchService.getInstance(BranchDao.getInstance(), BranchMapper.getInstance());
        serviceBranch.list();
        String branchName = getStr(LangConfig.get(language, "choose.branch") + " >> ");
        Branch branch = BranchDao.getInstance().findByNameWithNull(branchName);


        Print.println(GREEN, LangConfig.get(language, "create.admin.for.branch"));
        String userName = getStr(LangConfig.get(language, "username") + " >> ");
        String password = getStr(LangConfig.get(language, "password") + " >> ");
        ResponseEntity<String> response = service.create(userName, password, branch);
        showResponse(response);
    }

    public static void delete() {
        String userName = getStr(LangConfig.get(language, "username") + " >> ");
        ResponseEntity<String> response = service.delete(userName);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        String userName = getStr(LangConfig.get(language, "username") + " >> ");
        ResponseEntity<String> response = service.block(userName);
        showResponse(response);
    }

    public static void unblock() {
        String userName = getStr(LangConfig.get(language, "username") + " >> ");
        ResponseEntity<String> response = service.unblock(userName);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
