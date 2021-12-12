package uz.jl.services.atm;

import uz.jl.configs.Session;
import uz.jl.dao.atm.AtmDao;


import uz.jl.dao.db.FRWAtm;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.enums.atm.CassetteStatus;
import uz.jl.enums.auth.Role;
import uz.jl.enums.branch.BranchStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.atm.Atm;


import uz.jl.models.atm.Cassette;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;


import java.util.*;

import static uz.jl.ui.BranchUI.unblockList;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * @author Elmurodov Javohir, Mon 10:46 AM. 12/6/2021
 */
public class AtmService
        extends BaseAbstractService<Atm, AtmDao, ATMMapper>
        implements IBaseCrudService<Atm> {
    Role role = Session.getInstance().getUser().getRole();
    ArrayList<Cassette> cassettes = new ArrayList<>(4);

    private static AtmService service;

    public static AtmService getInstance(AtmDao repository, ATMMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AtmService(repository, mapper);
        }
        return service;
    }

    public AtmService(AtmDao repository, ATMMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(String name) {
        if (!role.equals(Role.ADMIN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        ATMType.show();
        String typeStr = getStr("?:").toUpperCase(Locale.ROOT);
        while (Objects.isNull(ATMType.getByValue(typeStr))) {
            println(RED, "Enter true 😒");
            ATMType.show();
            typeStr = getStr("?:").toUpperCase(Locale.ROOT);
        }
        ATMType type1 = ATMType.valueOf(typeStr);
        Atm atm = new Atm(name, type1, Session.getInstance().getUser());
        atm.setBranchId(Session.getInstance().getUser().getBranchId());
        getCassetes();
        for (int i = 0; i < cassettes.size(); i++) {
            String a = getStr(cassettes.get(i).getCurrencyValue() + "->");
            while (!checkNum(a)) {
                println(RED, "Tekshirganman 🤣");
                a = getStr(cassettes.get(i).getCurrencyValue() + "->");
            }
            cassettes.get(i).setCurrencyCount(Integer.valueOf(a));
        }
        atm.setCassettes(cassettes);
        return create(atm);
    }

    private boolean checkNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;

    }

    private void getCassetes() {
        Cassette cassette1 = new Cassette();
        Cassette cassette2 = new Cassette();
        Cassette cassette3 = new Cassette();
        Cassette cassette4 = new Cassette();
        cassette1.setCurrencyValue("100000");
        cassette1.setStatus(CassetteStatus.ACTIVE);
        cassettes.add(cassette1);
        cassette2.setCurrencyValue("50000");
        cassette2.setStatus(CassetteStatus.ACTIVE);
        cassettes.add(cassette2);
        cassette3.setCurrencyValue("10000");
        cassette3.setStatus(CassetteStatus.ACTIVE);
        cassettes.add(cassette3);
        cassette4.setCurrencyValue("5000");
        cassette4.setStatus(CassetteStatus.ACTIVE);
        cassettes.add(cassette4);
    }

    @Override
    public ResponseEntity<String> create(Atm atm) {
        if (repository.hasSuchName(atm.getName())) {
            return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
        }
        FRWAtm.getInstance().writeAll(atm);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> delete(String name) {
        if (!role.equals(Role.ADMIN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        Atm atm;
        try {
            atm = repository.findByName(name);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return delete(atm);
    }

    @Override
    public ResponseEntity<String> delete(Atm atm) {
        if (atm.getDeleted() == 1) {
            return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
        }

        atm.setDeleted(1);
        FRWAtm.getInstance().writeAll(getAll());
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public void list() {
        if (!role.equals(Role.ADMIN)) {
            Print.println(Color.RED, "Forbidden");
            return;// new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        boolean stop = true;
        for (Atm atm : FRWAtm.getInstance().getAll()) {
            if (Session.getInstance().getUser().getBranchId().equals(atm.getBranchId())) {
                if (atm.getDeleted() == 0) {
                    stop = false;
                    if (atm.getStatus().equals(ATMStatus.BLOCKED))
                        Print.println(Color.RED, atm.getName());
                    else
                        Print.println(Color.PURPLE, atm.getName());
                }
            }
        }
        if (stop)
            println(RED, "You haven't ATM 🤔");
    }

    @Override
    public Atm get(String id) {
        return null;
    }

    @Override
    public List<Atm> getAll() {
        return FRWAtm.getInstance().getAll();
    }

    public ResponseEntity<String> update(String oldName, String newName) {
        if (!(Role.ADMIN.equals(role) || Role.HR.equals(role))) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        try {
            Atm atm = repository.findByName(oldName);
            if (atm.getDeleted() == 1) {
                throw new APIException("ATM Not Found", HttpStatus.HTTP_404);
            }
            if (atm.getStatus().equals(ATMStatus.BLOCKED)) {
                return new ResponseEntity<>("This ATM is Blocked", HttpStatus.HTTP_406);
            }
            if (repository.hasSuchName(newName)) {
                return new ResponseEntity<>("Already exists", HttpStatus.HTTP_406);
            }
            return update(newName, atm);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    @Override
    public ResponseEntity<String> update(String newName, Atm atm) {
        atm.setName(newName);
        atm.setUpdatedAt(new Date());
        atm.setUpdatedBy(Session.getInstance().getUser().getUsername());
        FRWAtm.getInstance().writeAll(atm);
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> block(String name) {
        if (!role.equals(Role.ADMIN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        if (unblockCount() == 0) {
            return new ResponseEntity<>("Not Found Any Unblocked Branch", HttpStatus.HTTP_404);
        }
//        unblockList();
        try {
            Atm atm = repository.findByName(name);
            if (atm.getDeleted() == 1) {
                throw new APIException("ATM Not Found", HttpStatus.HTTP_404);
            }
            if (atm.getStatus().equals(ATMStatus.BLOCKED)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (atm.getStatus().equals(ATMStatus.ACTIVE)) {
                atm.setStatus(ATMStatus.BLOCKED);
            }
            FRWAtm.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public ResponseEntity<String> unblock(String name) {
        if (!role.equals(Role.ADMIN)) {
            return new ResponseEntity<>("Forbidden", HttpStatus.HTTP_403);
        }
        if (blockCount() == 0) {
            return new ResponseEntity<>("Not Found Any Blocked Branch", HttpStatus.HTTP_404);
        }
//        blockList();
        try {
            Atm atm = repository.findByName(name);
            if (atm.getStatus().equals(ATMStatus.ACTIVE)) {
                return new ResponseEntity<>("Already done", HttpStatus.HTTP_406);
            }
            if (atm.getStatus().equals(ATMStatus.BLOCKED)) {
                atm.setStatus(ATMStatus.ACTIVE);
            }
            FRWAtm.getInstance().writeAll(getAll());
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
        return new ResponseEntity<>("Successfully done", HttpStatus.HTTP_200);
    }

    public void blockList() {
        if (role.notIn(Role.ADMIN)) {
            Print.println(Color.RED, "Forbidden");
            return;
        }
        for (Atm atm : FRWAtm.getInstance().getAll()) {
            if (atm.getDeleted() == 0 && atm.getStatus().equals(ATMStatus.BLOCKED) && atm.getBranchId().equals(Session.getInstance().getUser().getBranchId()))
                Print.println(Color.RED, atm.getName());
        }
    }

    public void unblockList() {
        for (Atm atm : FRWAtm.getInstance().getAll()) {
            if (atm.getDeleted() == 0 && atm.getStatus().equals(ATMStatus.ACTIVE))
                Print.println(atm.getName());
        }
    }

    public Integer blockCount() {
        Integer count = 0;
        for (Atm atm : FRWAtm.getInstance().getAll()) {
            if (atm.getBranchId().equals(Session.getInstance().getUser().getBranchId())) {
                if (atm.getDeleted() == 0 && atm.getStatus().equals(ATMStatus.BLOCKED))
                    count++;
            }
        }
        return count;
    }

    public Integer unblockCount() {
        Integer count = 0;
        for (Atm atm : FRWAtm.getInstance().getAll()) {
            if (atm.getBranchId().equals(Session.getInstance().getUser().getBranchId())) {
                if (atm.getDeleted() == 0 && atm.getStatus().equals(ATMStatus.ACTIVE))
                    count++;
            }
        }
        return count;
    }
}
