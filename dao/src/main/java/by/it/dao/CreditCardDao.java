package by.it.dao;

import by.it.model.CreditCard;
import java.util.List;

public interface CreditCardDao extends IBaseDao<Long, CreditCard> {

    List<CreditCard> lockedCardList();

    List<CreditCard> forLockCardList();
}
