package uz.jl.models.card;

import lombok.*;
import uz.jl.enums.atm.Status;
import uz.jl.enums.card.CardStatus;
import uz.jl.enums.card.CardType;
import uz.jl.models.Auditable;

import java.math.BigDecimal;

/**
 * @author Elmurodov Javohir, Mon 12:07 PM. 12/6/2021
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card extends Auditable {
    private String pan;
    private String expiry;
    private String password;
    private CardType type;
    private CardStatus status;
    private BigDecimal balance;
    private String bankId;
    private String holderId;
}
