package uz.jl.ui;

import uz.jl.dao.atm.AtmDao;
import uz.jl.enums.atm.ATMType;
import uz.jl.mapper.ATMMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.atm.AtmService;
import uz.jl.utils.Input;

import java.util.Locale;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class AtmUI {
    static AtmService service= uz.jl.services.atm.AtmService.getInstance(AtmDao.getInstance(), ATMMapper.getInstance());
    public static void create() {
        String name=getStr("ATM name:");
        ResponseEntity<String> response = service.create(name);
        showResponse(response);
    }

    public static void update() {
        String oldName=getStr("old ATM name:");
        String newName=getStr("new ATM name:");
        ResponseEntity<String> response = service.update(oldName,newName);
        showResponse(response);
    }

    public static void delete() {
        String name = Input.getStr("ATM name: ");
        ResponseEntity<String> response = service.delete(name);
        showResponse(response);
    }

    public static void list() {
         service.list();
    }

    public static void block() {
        String name=getStr("ATM name: ");
        ResponseEntity<String> response = service.block(name);
        showResponse(response);
    }

    public static void unblock() {
        String name = getStr("ATM name: ");
        ResponseEntity<String> response = service.unblock(name);
        showResponse(response);
    }

    public static void blockList() {
        service.blockList();
    }
}
