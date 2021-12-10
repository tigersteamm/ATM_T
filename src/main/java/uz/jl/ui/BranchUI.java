package uz.jl.ui;

import uz.jl.dao.branch.BranchDao;
import uz.jl.mapper.BranchMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.branch.BranchService;
import uz.jl.utils.Input;

import static uz.jl.ui.BaseUI.showResponse;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class BranchUI {
    // TODO: 09.12.2021 set check for admin
    static BranchService service = BranchService.getInstance(BranchDao.getInstance(), BranchMapper.getInstance());

    public static void create() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.create(name);
        showResponse(response);
    }

    public static void update() {

    }

    public static void delete() {
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.delete(name);
        showResponse(response);
    }

    public static void list() {
        service.list();
    }

    public static void block() {
        unblockList();
        String name = Input.getStr("Branch name = ");
        ResponseEntity<String> response = service.block(name);
        showResponse(response);
    }

    public static void unblock() {
        blockList();
        String name = Input.getStr("Branch name = ");
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
