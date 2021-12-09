package uz.jl.models.atm;

import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.models.Auditable;

/**
 * @author Elmurodov Javohir, Mon 12:10 PM. 11/29/2021
 */
public class ATMEntity extends Auditable {
    private String bankId;
    private ATMType type;
    private String name;
    private ATMStatus status;
    private double latitude;
    private double longitude;
}
