package by.it.service;

import by.it.model.CreditCard;
import java.util.List;

public interface CreditCardService extends DataService<CreditCard> {

    void lockCardByAdmin(CreditCard card);

    void lockCardByUser(CreditCard card);

    void unlockCardByAdmin(CreditCard card);

    void unlockCardByUser(CreditCard card);

    List<CreditCard> lockedCardList();

    List<CreditCard> forLockCardList();

}
