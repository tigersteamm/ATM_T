package uz.jl.models.card;

import lombok.*;
import uz.jl.enums.atm.Status;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.card.CardStatus;
import uz.jl.enums.card.CardType;
import uz.jl.models.Auditable;
import uz.jl.models.settings.Language;

import java.math.BigDecimal;
import java.util.Date;

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

    @Builder(builderMethodName = "childBuilder", buildMethodName = "childBuild")
    public Card(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted, String pan, String expiry, String password, CardType type, CardStatus status, BigDecimal balance, String bankId, String holderId) {
        super(createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.pan = pan;
        this.expiry = expiry;
        this.password = password;
        this.type = type;
        this.status = status;
        this.balance = balance;
        this.bankId = bankId;
        this.holderId = holderId;
    }
}
