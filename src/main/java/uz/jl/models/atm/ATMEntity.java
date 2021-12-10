package uz.jl.models.atm;

import lombok.Getter;
import lombok.Setter;
import uz.jl.configs.Session;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.models.Auditable;
import uz.jl.models.auth.AuthUser;

import java.util.Date;

import static uz.jl.utils.BaseUtil.generateUniqueID;

/**
 * @author Elmurodov Javohir, Mon 12:10 PM. 11/29/2021
 */
@Getter @Setter
public class ATMEntity extends Auditable {
    private String bankId;
    private ATMType type;
    private String name;
    private ATMStatus status;
    private double latitude;
    private double longitude;


    public ATMEntity(String atmName, ATMType atmType, String bankId, AuthUser user) {
        super(generateUniqueID(),user.getId());
        name=atmName;
        type=atmType;
        this.bankId=bankId;
    }
}
