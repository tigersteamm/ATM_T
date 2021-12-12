package uz.jl.ui;

import uz.jl.configs.LangConfig;
import uz.jl.configs.Session;
import uz.jl.dao.atm.AtmDao;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.mapper.ATMMapper;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.atm.AtmProcessService;
import uz.jl.services.client.ClientService;

import java.math.BigDecimal;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.utils.Input.getStr;

public class AtmProcessUI {
    static ClientService service = ClientService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();

    public static void smsInfo() {
    }

    public static void ChangePin() {
        String oldPin = getStr(LangConfig.get(language, "old.pin") + " >> ");
        String newPin = getStr(LangConfig.get(language, "new.pin") + " >> ");
        String newPinCheck = getStr(LangConfig.get(language, "new.pin") + " >> ");
        ResponseEntity<String> response = service.changePin(oldPin, newPin, newPinCheck);
        showResponse(response);
    }

    public static void cardBalance() {
        service.showCardBalance();
    }

    public static void cashWithdrawal() {
        String amount = getStr(LangConfig.get(language, "amount") + " >> ");
        ResponseEntity<String> response = service.cashWithdrawal(amount);
        showResponse(response);
    }

    public static void cardDeposit() {
    }

    public static void returnCard() {
        Session.getInstance().setCard(null);
        Session.getInstance().setAtm(null);
    }
}
