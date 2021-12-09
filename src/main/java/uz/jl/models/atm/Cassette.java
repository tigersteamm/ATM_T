package uz.jl.models.atm;

import lombok.*;
import uz.jl.enums.atm.CassetteStatus;

/**
 * @author Elmurodov Javohir, Mon 11:54 AM. 11/29/2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cassette {
    private String currencyValue;
    private CassetteStatus status;
    private Integer currencyCount;
}
