package uz.jl.dao.card;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.atm.BaseDao;
import uz.jl.dao.db.FRWCard;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.models.card.Card;
import uz.jl.utils.Print;

import java.util.Objects;

/**
 * @author D4uranbek сб. 17:57. 11.12.2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardDao extends BaseDao<Card> {
    FRWCard frwCard = FRWCard.getInstance();

    private static uz.jl.dao.card.CardDao dao;

    public static uz.jl.dao.card.CardDao getInstance() {
        if (Objects.isNull(dao))
            dao = new uz.jl.dao.card.CardDao();
        return dao;
    }

    public void ShowByHolderId(String id) {
        for (Card card : frwCard.getAll()) {
            if (card.getHolderId().equals(id)) {
                Print.println(card.getPan());
            }
        }
    }

    public boolean hasSuchPan(String pan) {
        for (Card card : frwCard.getAll()) {
            if (card.getPan().equals(pan)) {
                return true;
            }
        }
        return false;
    }

    public Card findByPan(String pan) throws APIException {
        for (Card card : frwCard.getAll()) {
            if (card.getPan().equals(pan))
                return card;
        }
        throw new APIException("Card Not Found", HttpStatus.HTTP_404);
    }
}

