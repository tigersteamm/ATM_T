package uz.jl.services.atm;

import uz.jl.dao.atm.ATMDao;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.atm.ATMEntity;
import uz.jl.services.BaseAbstractService;

import java.util.List;

/**
 * @author Elmurodov Javohir, Mon 10:46 AM. 12/6/2021
 */
public class AtmService extends BaseAbstractService<ATMEntity, ATMDao, ATMMapper> {

    public AtmService(ATMDao repository, ATMMapper mapper) {
        super(repository, mapper);
    }


}
