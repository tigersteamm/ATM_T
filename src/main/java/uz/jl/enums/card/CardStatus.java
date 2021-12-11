package uz.jl.enums.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Elmurodov Javohir, Mon 12:14 PM. 11/29/2021
 */
@Getter
@AllArgsConstructor
public enum CardStatus {
    ACTIVE(0),
    BLOCKED(-1),
    EXPIRED(-100);
    private final int code;
}
