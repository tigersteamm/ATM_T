package uz.jl.services.employee;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.exceptions.APIRuntimeException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

import static uz.jl.utils.BaseUtils.genId;
import static uz.jl.utils.Color.PURPLE;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;

public class EmployeeService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {
    private static EmployeeService service;

    public static EmployeeService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new EmployeeService(repository, mapper);
        }
        return service;
    }

    public EmployeeService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String userName, String password, String phoneNumber) {
        AuthUser user = new AuthUser();
        user.setId(genId());
        user.setUsername(userName);
        user.setPassword(password);
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(Role.EMPLOYEE);
        user.setPhoneNumber(phoneNumber);
        user.setCreatedBy(Session.getInstance().getUser().getId());
        user.setLanguage(Session.getInstance().getUser().getLanguage());
        return create(user);
    }

    @Override
    public ResponseEntity<String> create(AuthUser authUser) {
        if (repository.hasSuchName(authUser.getUsername())) {
            return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
        }
        FRWAuthUser.getInstance().writeAll(authUser);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }


    public ResponseEntity<String> delete(String userName) {
        AuthUser authUser;
        try {
            authUser = repository.findByUserName(userName);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return delete(authUser);
    }

    @Override
    public ResponseEntity<String> delete(AuthUser authUser) {
        if (authUser.getDeleted() == 1) {
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }

        authUser.setDeleted(1);
        FRWAuthUser.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public void list() {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0) {
                if (authUser.getStatus().equals(UserStatus.ACTIVE) && authUser.getRole().equals(Role.EMPLOYEE)) {
                    Print.println(PURPLE, authUser.getUsername());
                }
                if (authUser.getStatus().equals(UserStatus.BLOCKED) && authUser.getRole().equals(Role.EMPLOYEE)) {
                    Print.println(RED, authUser.getUsername());
                }
            }
        }
    }

    @Override
    public AuthUser get(String id) {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (id.equalsIgnoreCase(authUser.getId())) {
                return authUser;
            }
        }
        throw new APIRuntimeException("Employee not found", HttpStatus.HTTP_404.getCode());
    }

    @Override
    public List<AuthUser> getAll() {
        return FRWAuthUser.getInstance().getAll();
    }

    public ResponseEntity<String> block(String userName) {
        try {
            AuthUser authUser = repository.findByUserName(userName);
            if (authUser.getDeleted() == 1) {
                throw new APIException("Employee Not Found", HttpStatus.HTTP_404);
            }
            if (authUser.getStatus().equals(UserStatus.BLOCKED)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                authUser.setStatus(UserStatus.BLOCKED);
            }
            FRWAuthUser.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> unblock(String userName) {
        try {
            AuthUser authUser = repository.findByUserName(userName);
            if (authUser.getDeleted() == 1) {
                throw new APIException("Employee Not Found", HttpStatus.HTTP_404);
            }
            if (authUser.getStatus().equals(UserStatus.ACTIVE)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (authUser.getStatus().equals(UserStatus.BLOCKED)) {
                authUser.setStatus(UserStatus.ACTIVE);
            }
            FRWAuthUser.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> update(String id, AuthUser authUser) {
        return null;
    }

    public void blockList() {
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0 && authUser.getStatus().equals(UserStatus.BLOCKED))
                Print.println(Color.RED, authUser.getUsername());
        }
    }

    public Integer blockCount() {
        Integer count = 0;
        for (AuthUser authUser : FRWAuthUser.getInstance().getAll()) {
            if (authUser.getDeleted() == 0 && authUser.getStatus().equals(UserStatus.BLOCKED))
                count++;
        }
        return count;
    }

}
