package uz.jl.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author D4uranbek чт. 18:55. 09.12.2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchMapper {
    private static BranchMapper mapper;

    public static BranchMapper getInstance() {
        if (Objects.isNull(mapper)) {
            mapper = new BranchMapper();
        }
        return mapper;
    }
}
