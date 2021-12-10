package uz.jl.services.admin;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;

import java.util.List;
import java.util.Objects;

import static uz.jl.utils.BaseUtils.genId;

/**
 * @author Nodirbek Abdukarimov Fri. 11:19 AM. 12/10/2021
 */
public class AdminService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser> {

    private static AdminService service;

    public static AdminService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AdminService(repository, mapper);
        }
        return service;
    }


    public AdminService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String userName, String password, String phoneNumber) {
        try {
            AuthUser user = repository.findByUserName(userName);
            if (Objects.nonNull(user)) {
                return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
            }
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        AuthUser user = new AuthUser();
        user.setId(genId());
        user.setUsername(userName);
        user.setPassword(password);
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(Role.EMPLOYEE);
        user.setPhoneNumber(phoneNumber);
        user.setCreatedBy(Session.getInstance().getUser().getId());
        user.setLanguage(Session.getInstance().getUser().getLanguage());
        FRWAuthUser.getInstance().writeAll(user);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    @Override
    public void create(AuthUser authUser) {

    }

    @Override
    public ResponseEntity<String> create(String userName, String password) {
        try {
            AuthUser user = repository.findByUserName(userName);
            if (Objects.nonNull(user)) {
                return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
            }
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }

        AuthUser user = new AuthUser();
        user.setId(genId());
        user.setUsername(userName);
        user.setPassword(password);
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(Role.ADMIN);
        user.setCreatedBy(Session.getInstance().getUser().getId());
        user.setLanguage(Session.getInstance().getUser().getLanguage());
        FRWAuthUser.getInstance().writeAll(user);
        return new ResponseEntity<>("Successfully created Admin", HttpStatus.HTTP_200);
    }

    @Override
    public ResponseEntity<String> delete(AuthUser authUser) {
        return null;
    }

    @Override
    public Branch get(String id) {
        return null;
    }

    @Override
    public List<AuthUser> getAll() {
        return null;
    }

    @Override
    public void update(String id, AuthUser authUser) {

    }
}