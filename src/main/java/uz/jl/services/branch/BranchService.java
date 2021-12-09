package uz.jl.services.branch;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.branch.BranchStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.BranchMapper;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author D4uranbek чт. 18:45. 09.12.2021
 */
public class BranchService
        extends BaseAbstractService<Branch, BranchDao, BranchMapper>
        implements IBaseCrudService<Branch> {

    private static BranchService service;

    public static BranchService getInstance(BranchDao repository, BranchMapper mapper) {
        if (Objects.isNull(service)) {
            service = new BranchService(repository, mapper);
        }
        return service;
    }

    public BranchService(BranchDao repository, BranchMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String name) {
        if (repository.hasSuchName(name)) {
            return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
        }
        Branch branch = new Branch();
        branch.setName(name);
        branch.setBankId(Session.getInstance().getUser().getBankId());
        branch.setStatus(BranchStatus.ACTIVE);
        branch.setCreatedAt(new Date());
        branch.setCreatedBy(Session.getInstance().getUser().getId());

        FRWBranch.getInstance().writeAll(branch);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> delete(String name, String a) {
        try {
            repository.findByName(name).setDeleted(1);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        FRWBranch.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    @Override
    public void create(Branch branch) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Branch get(String id) {
        return null;
    }

    @Override
    public List<Branch> getAll() {
        return null;
    }

    @Override
    public void update(String id, Branch branch) {

    }
}
