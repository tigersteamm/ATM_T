package uz.jl.services.branch;

import uz.jl.configs.Session;
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
import uz.jl.utils.Color;
import uz.jl.utils.Print;

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
        branch.setDeleted(0);

        create(branch);
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

    public void list() {
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            Print.println(Color.PURPLE, branch.getName());
        }
    }

    @Override
    public void create(Branch branch) {
        FRWBranch.getInstance().writeAll(branch);
    }

    @Override
    public void delete(String id) {
        try {
            repository.findById(id).setDeleted(1);
        } catch (APIException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        FRWBranch.getInstance().writeAll(getAll());
//        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    @Override
    public Branch get(String id) {
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (id.equals(branch.getId()))
                return branch;
        }
        return null;
    }

    @Override
    public List<Branch> getAll() {
        return FRWBranch.getInstance().getAll();
    }

    @Override
    public void update(String id, Branch branch) {
        // TODO: 09.12.2021 some logic
        branch.setUpdatedAt(new Date());
        branch.setUpdatedBy(id);
        FRWBranch.getInstance().writeAll(getAll());
    }

    public void blockList() {
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getStatus().equals(BranchStatus.BLOCKED))
                Print.println(Color.RED, branch.getName());
        }
    }

    public ResponseEntity<String> block(String name) {
        try {
            Branch branch = repository.findByName(name);
            if (branch.getStatus().equals(BranchStatus.BLOCKED)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (branch.getStatus().equals(BranchStatus.ACTIVE)) {
                branch.setStatus(BranchStatus.BLOCKED);
            }
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        FRWBranch.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> unblock(String name) {
        try {
            Branch branch = repository.findByName(name);
            if (branch.getStatus().equals(BranchStatus.ACTIVE)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (branch.getStatus().equals(BranchStatus.BLOCKED)) {
                branch.setStatus(BranchStatus.ACTIVE);
            }
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        FRWBranch.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }
}
