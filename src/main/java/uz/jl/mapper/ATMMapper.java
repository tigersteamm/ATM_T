package uz.jl.mapper;

import uz.jl.dto.atm.ATMDto;
import uz.jl.models.atm.ATMEntity;

/**
 * @author Elmurodov Javohir, Mon 11:18 AM. 12/6/2021
 */
public class ATMMapper extends BaseMapper<ATMEntity, ATMDto> {
    @Override
    ATMEntity to(ATMDto atmDto) {
        return null;
    }

    @Override
    ATMDto from(ATMEntity atmEntity) {
        return null;
    }
}
