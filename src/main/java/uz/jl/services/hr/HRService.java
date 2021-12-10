package uz.jl.services.hr;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.Role;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.services.BaseAbstractService;


import static uz.jl.utils.Input.getStr;

public class HRService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {

    public HRService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public static void createHR() {
        String username = getStr("Enter HR username : ");
        String password = getStr("Enter HR password : ");
        String phoneNumber = getStr("Phone number : ");
        AuthUser hr = new AuthUser();
        hr.setUsername(username);
        hr.setPassword(password);
        hr.setPhoneNumber(phoneNumber);
        hr.setLanguage(Session.getInstance().getUser().getLanguage());
        hr.setRole(Role.HR);



    }
}
