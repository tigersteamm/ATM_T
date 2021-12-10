package uz.jl.services.hr;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.services.BaseAbstractService;
import uz.jl.utils.Print;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static uz.jl.utils.Input.getStr;

public class HRService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {

    public HRService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public static void createHR() {
        List<AuthUser> hrList = FRWAuthUser.getInstance().getAll();
        String username = getStr("Enter HR username : ");
        if (checkForTheSameUsername(username, hrList)) {
            String password = getStr("Enter HR password : ");
            String phoneNumber = getStr("Phone number : ");
            AuthUser hr = new AuthUser();
            hr.setLanguage(Session.getInstance().getUser().getLanguage());
            hr.setPhoneNumber(phoneNumber);
            hr.setUsername(username);
            hr.setPassword(password);
            hr.setRole(Role.HR);
            FRWAuthUser.getInstance().writeAll(hr);
            Print.println("Successfully");
        } else {
            Print.println("The username already has taken");
        }
    }


    private static boolean checkForTheSameUsername(String username, List<AuthUser> hrList) {
        boolean check = true;
        for (AuthUser hr1 : hrList) {
            if (hr1.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}
