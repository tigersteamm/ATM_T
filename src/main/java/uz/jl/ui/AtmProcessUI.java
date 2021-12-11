package uz.jl.ui;

import uz.jl.dao.atm.AtmDao;
import uz.jl.mapper.ATMMapper;
import uz.jl.services.atm.AtmProcessService;
import uz.jl.services.atm.AtmService;

public class AtmProcessUI {
    static AtmProcessService service= uz.jl.services.atm.AtmProcessService.getInstance(AtmDao.getInstance(), ATMMapper.getInstance());


    public static void infoCard() {
        service.infoCard();
    }
}
