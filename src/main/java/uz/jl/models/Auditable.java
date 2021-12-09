package uz.jl.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.jl.configs.Session;
import uz.jl.utils.BaseUtils;

import java.util.Date;

/**
 * @author Elmurodov Javohir, Mon 11:40 AM. 12/6/2021
 */

@Getter
@Setter
@ToString
public class Auditable implements BaseEntity {
    private String id;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
    private int deleted;

    public Auditable() {
        this.createdAt = new Date();
    }

    @Builder(builderMethodName = "parentBuilder", buildMethodName = "parentBuild")
    public Auditable(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deleted = deleted;
    }
}
