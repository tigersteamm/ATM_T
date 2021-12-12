package uz.jl.dao.atm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.configs.Session;
import uz.jl.dao.db.FRWAtm;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.atm.Atm;


import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtmDao extends BaseDao<Atm> {
    static FRWAtm frwATMEntity = FRWAtm.getInstance();
    private static AtmDao dao;

    public static AtmDao getInstance() {
        if (Objects.isNull(dao))
            dao = new AtmDao();
        return dao;
    }

    public Atm findById(String id) throws APIException {
        for (Atm atm : frwATMEntity.getAll()) {
            if (id.equals(atm.getId()))
                if (atm.getDeleted() == 0) {
                    return atm;
                } else break;
        }
        throw new APIException("ATMEntity Not Found", HttpStatus.HTTP_404);
    }

    public boolean hasSuchName(String name) {
        for (Atm atm : frwATMEntity.getAll()) {
            if (name.equalsIgnoreCase(atm.getName())) {
                return true;
            }
        }
        return false;
    }

    public static Atm findByName(String name) throws APIException {
        for (Atm atm : frwATMEntity.getAll()) {
            if (name.equalsIgnoreCase(atm.getName())) //&& Session.getInstance().getUser().getBranchId().equals(atm.getBranchId())
                if (atm.getDeleted() == 0) {
                    return atm;
                } else break;
        }
        throw new APIException("ATMEntity Not Found", HttpStatus.HTTP_404);
    }
}
