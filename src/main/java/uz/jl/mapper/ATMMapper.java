package uz.jl.mapper;

import uz.jl.dto.atm.ATMDto;
import uz.jl.models.atm.Atm;

import java.util.Objects;

/**
 * @author Elmurodov Javohir, Mon 11:18 AM. 12/6/2021
 */
public class ATMMapper extends BaseMapper<Atm, ATMDto> {
    private static ATMMapper mapper;

    public static ATMMapper getInstance() {
        if (Objects.isNull(mapper)) {
            mapper = new ATMMapper();
        }
        return mapper;
    }
    @Override
    Atm to(ATMDto atmDto) {
        return null;
    }

    @Override
    ATMDto from(Atm atmEntity) {
        return null;
    }
}
