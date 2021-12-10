package uz.jl.models.branch;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.jl.enums.atm.Status;
import uz.jl.enums.branch.BranchStatus;
import uz.jl.models.Auditable;

import java.util.Date;

/**
 * @author D4uranbek чт. 18:06. 09.12.2021
 */

@Getter
@Setter
public class Branch extends Auditable {
    private String name;
    private String bankId;
    private BranchStatus status;

    public Branch() {
        this.status = BranchStatus.ACTIVE;
    }

    @Builder(builderMethodName = "childBuilder", buildMethodName = "childBuild")
    public Branch(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted, String name, String bankId, BranchStatus status) {
        super(createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.name = name;
        this.bankId = bankId;
        this.status = status;
    }
}
