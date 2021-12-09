package uz.jl.enums.branch;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author D4uranbek чт. 18:13. 09.12.2021
 */

@Getter
@AllArgsConstructor
public enum BranchStatus {
    BLOCKED(-1),
    ACTIVE(0);

    private final int code;
}
