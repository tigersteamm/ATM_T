package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.db.FRWATMEntity;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.models.atm.ATMEntity;
import uz.jl.ui.menus.MenuKey;

import java.util.*;

import static uz.jl.enums.atm.ATMType.HUMO;
import static uz.jl.enums.atm.ATMType.UZCARD;
import static uz.jl.utils.Color.BLUE;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class AtmUI {
    public static void create() {
        String ATMName=getStr("Enter ATM name:");
        ATMType atmType=ATMtypeWR();
        if(Objects.isNull(atmType)){
            println(RED,"Wrong Type entered 😒 ");
            return;
        }
        ATMEntity atm=new ATMEntity(ATMName,atmType,Session.getInstance().getUser().getBankId(), Session.getInstance().getUser());
        FRWATMEntity.getInstance().writeAll(atm);
    }

    public static void update() {

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

//

    private static ATMType ATMtypeWR() {
        ATMType.show();
        String atmType=getStr("?:").toUpperCase(Locale.ROOT);
        return ATMType.getByValue(atmType);
    }
}
