package by.it.service;

import by.it.model.CreditCard;
import java.util.List;

public interface CreditCardService extends DataService<CreditCard> {

    void lockCard(CreditCard card);

    void unlockCard(CreditCard card);

    List<CreditCard> lockedCardList();

    List<CreditCard> forLockCardList();

}
