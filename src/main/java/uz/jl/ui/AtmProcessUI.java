package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.atm.AtmDao;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.settings.Language;
import uz.jl.services.atm.AtmProcessService;

public class AtmProcessUI {
    static AtmProcessService service = uz.jl.services.atm.AtmProcessService.getInstance(AtmDao.getInstance(), ATMMapper.getInstance());
    static Language language = Session.getInstance().getUser().getLanguage();


    public static void infoCard() {
        service.infoCard();
    }
}
