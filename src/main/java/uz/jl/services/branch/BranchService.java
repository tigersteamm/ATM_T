package uz.jl.services.branch;

import uz.jl.configs.Session;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.auth.Role;
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

    Role role = Session.getInstance().getUser().getRole();

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
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
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

    @Override
    public void create(Branch branch) {
        FRWBranch.getInstance().writeAll(branch);
    }

    @Override
    public ResponseEntity<String> create(String userName, String password) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Branch branch) {
        return null;
    }

    public ResponseEntity<String> delete(String name, String undefined) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        try {
            Branch branch = repository.findByName(name);
            if (branch.getDeleted() == 1)
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            branch.setDeleted(1);
            FRWBranch.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public void list() {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            Print.println(Color.RED, "Forbidden");
            return; // new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0) {
                if (branch.getStatus().equals(BranchStatus.BLOCKED))
                    Print.println(Color.RED, branch.getName());
                else
                    Print.println(Color.PURPLE, branch.getName());
            }
        }
    }

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

    public ResponseEntity<String> block(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        if (unblockCount() == 0) {
            return new ResponseEntity<>("Not Found Any Unblocked Branch", HttpStatus.HTTP_404);
        }
        unblockList();
        try {
            Branch branch = repository.findByName(name);
            if (branch.getDeleted() == 1) {
                throw new APIException("Branch Not Found", HttpStatus.HTTP_404);
            }
            if (branch.getStatus().equals(BranchStatus.BLOCKED)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (branch.getStatus().equals(BranchStatus.ACTIVE)) {
                branch.setStatus(BranchStatus.BLOCKED);
            }
            FRWBranch.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> unblock(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        if (blockCount() == 0) {
            return new ResponseEntity<>("Not Found Any Blocked Branch", HttpStatus.HTTP_404);
        }
        blockList();
        try {
            Branch branch = repository.findByName(name);
            if (branch.getDeleted() == 1) {
                throw new APIException("Branch Not Found", HttpStatus.HTTP_404);
            }
            if (branch.getStatus().equals(BranchStatus.ACTIVE)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (branch.getStatus().equals(BranchStatus.BLOCKED)) {
                branch.setStatus(BranchStatus.ACTIVE);
            }
            FRWBranch.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public void blockList() {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            Print.println(Color.RED, "Forbidden");
            return; // new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.BLOCKED))
                Print.println(Color.RED, branch.getName());
        }
    }

    public void unblockList() {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            Print.println(Color.RED, "Forbidden");
            return; // new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.ACTIVE))
                Print.println(Color.PURPLE, branch.getName());
        }
    }

    public Integer blockCount() {
        Integer count = 0;
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.BLOCKED))
                count++;
        }
        return count;
    }

    public Integer unblockCount() {
        Integer count = 0;
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (branch.getDeleted() == 0 && branch.getStatus().equals(BranchStatus.ACTIVE))
                count++;
        }
        return count;
    }
}
