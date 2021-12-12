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
public class Atm extends Auditable {
    private String branchId;
    private ATMType type;
    private String name;
    private ATMStatus status;
    private double latitude;
    private double longitude;


    public Atm(String name,ATMType atmType, AuthUser user) {
        super(generateUniqueID(),user.getId());
        this.name=name;
        type=atmType;
        status=ATMStatus.ACTIVE;
    }
}
