package uz.jl.services.atm;

import uz.jl.dao.atm.AtmDao;
import uz.jl.enums.card.CardType;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.atm.Atm;
//import uz.jl.models.card.Cards;
import uz.jl.response.Data;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.utils.Print;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

public class AtmProcessService
        extends BaseAbstractService<Atm, AtmDao, ATMMapper>{
    private static AtmProcessService service;
    public static AtmProcessService getInstance(AtmDao repository, ATMMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AtmProcessService(repository, mapper);
        }
        return service;
    }

    public AtmProcessService(AtmDao repository, ATMMapper mapper) {
        super(repository, mapper);
    }
    public void infoCard() {
        Date date=new Date();
        Print.println("Date :  "+date);
        Print.println("Short information about card ");

    }

}
