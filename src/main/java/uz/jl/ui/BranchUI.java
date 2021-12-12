package uz.jl.ui;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.branch.BranchDao;
import uz.jl.mapper.BranchMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.branch.BranchService;
import uz.jl.utils.Input;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class BranchUI {
    static BranchService service = BranchService.getInstance(BranchDao.getInstance(), BranchMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void create() {
        String name = Input.getStr(LangConfig.get(language, "branch.name"));
        ResponseEntity<String> response = service.create(name);
        showResponse(response);
    }

    public static void update() {
        String oldName = getStr(LangConfig.get(language, "old.branch.name"));
        String newName = getStr(LangConfig.get(language, "new.branch.name"));
        ResponseEntity<String> response = service.update(oldName, newName);
        showResponse(response);
    }

    public static void delete() {
        String name = Input.getStr(LangConfig.get(language, "branch.name"));
        ResponseEntity<String> response = service.delete(name);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        unblockList();
        String name = Input.getStr(LangConfig.get(language, "branch.name"));
        ResponseEntity<String> response = service.block(name);
        showResponse(response);
    }

    public static void unblock() {
        blockList();
        String name = Input.getStr(LangConfig.get(language, "branch.name"));
        ResponseEntity<String> response = service.unblock(name);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }

    public static void unblockList() {
        service.unblockList();
    }
}
