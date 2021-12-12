package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.atm.AtmDao;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.settings.Language;
import uz.jl.services.atm.AtmProcessService;

import static uz.jl.utils.Input.getStr;

public class AtmProcessUI {
    public static AtmProcessService service = uz.jl.services.atm.AtmProcessService.getInstance(AtmDao.getInstance(), ATMMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void infoCard() {
        service.infoCard();
    }

    public static void enter() {
        service.showAtmMenu();
        String choice = getStr(">>>>");


    }

    public static void smsInfo() {
    }

    public static void ChangePin() {
    }

    public static void cardBalance() {
    }

    public static void cashWithdrawal() {
    }

    public static void cardDeposit() {
    }

    public static void returnCard() {
    }
}
