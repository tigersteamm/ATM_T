package uz.jl.dao.branch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.branch.Branch;

import java.util.Objects;

/**
 * @author D4uranbek чт. 18:48. 09.12.2021
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchDao extends BaseDao<Branch> {
    FRWBranch frwBranch = FRWBranch.getInstance();

    private static BranchDao dao;

    public static BranchDao getInstance() {
        if (Objects.isNull(dao))
            dao = new BranchDao();
        return dao;
    }

    public Branch findById(String id) throws APIException {
        for (Branch branch : frwBranch.getAll()) {
            if (id.equals(branch.getId()))
                if (branch.getDeleted() == 0) {
                    return branch;
                } else break;
        }
        throw new APIException("Branch not Found", HttpStatus.HTTP_404);
    }

    public boolean hasSuchName(String name) {
        for (Branch branch : frwBranch.getAll()) {
            if (name.equalsIgnoreCase(branch.getName())) {
                return true;
            }
        }
        return false;
    }

    public Branch findByName(String name) throws APIException {
        for (Branch branch : frwBranch.getAll()) {
            if (name.equalsIgnoreCase(branch.getName()))
//                if (branch.getDeleted() == 0) {
                return branch;
//                } else break;
        }
        throw new APIException("Branch Not Found", HttpStatus.HTTP_404);
    }
}
