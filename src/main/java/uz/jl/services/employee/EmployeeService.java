package uz.jl.services.employee;

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

    public ResponseEntity<String> create(String userName, String password, String phoneNumber)  {
      try {
          AuthUser user = repository.findByUserName(userName);
          if (Objects.nonNull(user)){
              return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
          }
      }catch (APIException e){
          return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
      }
        AuthUser user=new AuthUser();
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


    public static void delete() {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        if (list() == 1) {
            Print.println(RED, "Employee not found !");
            return;
        }
        String username = getStr("Username-> ");
        String name = findByUsername(username);
        if (Objects.nonNull(name)) {
            for (AuthUser user : users) {
                if (user.getUsername().equals(name)) {
                    users.remove(user);
                    FRWAuthUser.getInstance().writeAll(users);
                    Print.println(PURPLE, "Successfully Deleted");
                    return;
                }
            }
        }
        Print.println(RED, "Employee not found !");
    }

    public static int list() {
        int k = 1;
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getRole().equals(Role.EMPLOYEE)) {
                if (user.getStatus().equals(UserStatus.ACTIVE))
                    Print.println(k++ + ". " + PURPLE, user.getUsername());
                else
                    Print.println(k++ + ". " + RED, user.getUsername());
            }
        }
        return k;
    }

    public static void block() {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        if (list() == 1) {
            Print.println(RED, "Employee not found !");
            return;
        }
        String username = getStr("Username -> ");
        String name = findByUsername(username);
        if (Objects.nonNull(name)) {
            for (AuthUser user : users) {
                if (user.getUsername().equals(name)) {
                    if (user.getStatus().equals(UserStatus.BLOCKED)) {
                        Print.println(RED, "Bu Oldin bloklanganku 😐");
                        return;
                    }
                    user.setStatus(UserStatus.BLOCKED);
                    FRWAuthUser.getInstance().writeAll(users);
                    Print.println(PURPLE, "Successfully Blocked");
                    return;
                }
            }
        }
        Print.println(RED, "Employee not found !");
    }

    public static void unBlock() {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        if (blockList() == 1) {
            Print.println(RED, "Employee not found !");
            return;
        }
        String username = getStr("Username -> ");
        String name = findByUsername(username);
        if (Objects.nonNull(name)) {
            for (AuthUser user : users) {
                if (user.getUsername().equals(name)) {
                    user.setStatus(UserStatus.ACTIVE);
                    FRWAuthUser.getInstance().writeAll(users);
                    Print.println(PURPLE, "Successfully UnBlocked");
                    return;
                }
            }
        }
        Print.println(RED, "Employee not found !");

    }

    public static int blockList() {
        int k = 1;
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getStatus().equals(UserStatus.BLOCKED)) {
                Print.println(k++ + ". " + RED, user.getUsername());
                k++;
            }
        }
        return k;
    }

    private static String findByUsername(String userName) {
        List<AuthUser> users = FRWAuthUser.getInstance().getAll();
        for (AuthUser user : users) {
            if (user.getUsername().equals(userName)) {
                return userName;
            }
        }
        return null;
    }

    @Override
    public void create(AuthUser user) {

    }

    @Override
    public ResponseEntity<String> create(String userName, String password) {
        return null;
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
    public void update(String id, AuthUser user) {

    }
}
