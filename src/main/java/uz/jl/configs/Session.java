package uz.jl.configs;

import lombok.Getter;
import lombok.Setter;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.atm.Atm;
import uz.jl.models.card.Card;

import java.util.Objects;

/**
 * @author Elmurodov Javohir, Mon 11:39 AM. 12/6/2021
 */
public class Session {
    @Getter
    @Setter
    private AuthUser user;
    @Getter
    @Setter
    private Atm atm;
    @Getter
    @Setter
    private Card card;
    private static Session session;

    private Session() {
        user = new AuthUser();
//        atm = new Atm();
//        card = new Card();
    }

    public static Session getInstance() {
        if (Objects.isNull(session)) {
            session = new Session();
        }
        return session;
    }

}
