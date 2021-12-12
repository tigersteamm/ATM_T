package uz.jl.services.branch;

import uz.jl.configs.Session;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.db.FRWAtm;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.auth.Role;
import uz.jl.enums.branch.BranchStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.exceptions.APIRuntimeException;
import uz.jl.mapper.BranchMapper;
import uz.jl.models.atm.Atm;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.BaseUtils.genId;

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
        Branch branch = new Branch();
        branch.setName(name);
        branch.setId(genId());
        branch.setBankId(Session.getInstance().getUser().getBankId());
        branch.setStatus(BranchStatus.ACTIVE);
        branch.setCreatedAt(new Date());
        branch.setCreatedBy(Session.getInstance().getUser().getId());
        branch.setDeleted(0);

        return create(branch);
    }

    @Override
    public ResponseEntity<String> create(Branch branch) {
        if (repository.hasSuchName(branch.getName())) {
            return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
        }
        FRWBranch.getInstance().writeAll(branch);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> delete(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        Branch branch;
        try {
            branch = repository.findByName(name);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return delete(branch);
    }

    @Override
    public ResponseEntity<String> delete(Branch branch) {
        if (branch.getDeleted() == 1) {
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }

        branch.setDeleted(1);
        FRWBranch.getInstance().writeAll(getAll());
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

    @Override
    public Branch get(String id) {
        for (Branch branch : FRWBranch.getInstance().getAll()) {
            if (id.equals(branch.getId()))
                return branch;
        }
        throw new APIRuntimeException("Branch Not Found", HttpStatus.HTTP_404.getCode());
    }

    @Override
    public List<Branch> getAll() {
        return FRWBranch.getInstance().getAll();
    }

    public ResponseEntity<String> update(String oldName, String newName) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        try {
            Branch branch = repository.findByName(oldName);
            if (branch.getDeleted() == 1) {
                return new ResponseEntity<>("Branch Not Found", HttpStatus.HTTP_404);
            }
            if (branch.getStatus().equals(BranchStatus.BLOCKED)) {
                return new ResponseEntity<>("Branch is Blocked", HttpStatus.HTTP_406);
            }
            if (repository.hasSuchName(newName)) {
                return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
            }
            return update(newName, branch);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    @Override
    public ResponseEntity<String> update(String newName, Branch branch) {
        branch.setName(newName);
        branch.setUpdatedAt(new Date());
        branch.setUpdatedBy(Session.getInstance().getUser().getId());
        FRWBranch.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> block(String name) {
        if (!(Role.SUPER_ADMIN.equals(role) || Role.ADMIN.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        if (unblockCount() == 0) {
            return new ResponseEntity<>("Not Found Any Unblocked Branch", HttpStatus.HTTP_404);
        }
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
