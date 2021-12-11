package uz.jl.enums.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jl.enums.atm.ATMType;
import uz.jl.utils.Print;

/**
 * @author Elmurodov Javohir, Mon 12:21 PM. 12/6/2021
 */

@Getter
@AllArgsConstructor
public enum CardType {
    UZCARD("8600"),
    HUMO("9860"),
    MASTER_CARD("4853"),
    VISA("4735"),
    COBAGE("6330"),
    UNION_PAY("6262"),
    UNDEFINED("-1");
    private final String code;

    public static void show() {
        for (CardType type : values()) {
            Print.println(type);
        }
    }

    public static CardType getByString(String type) {
        for (CardType value : values()) {
            if (value.name().equalsIgnoreCase(type))
                return value;
        }
        return UNDEFINED;
    }

    public static boolean isValidCardType(String type) {
        for (CardType value : values()) {
            if (value.name().equalsIgnoreCase(type))
                return true;
        }
        return false;
    }
}
