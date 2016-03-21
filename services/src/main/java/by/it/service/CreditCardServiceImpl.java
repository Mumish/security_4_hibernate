package by.it.service;

import by.it.dao.CreditCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.it.model.CreditCard;
import java.util.List;

@Service("creditCardService")
@Transactional
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardDao creditCardDao;
    @Autowired
    private UserService userService;

    public void persist(CreditCard creditCard) {
        creditCardDao.persist(creditCard);
    }

    public void saveOrUpdate(CreditCard creditCard) {
        creditCardDao.saveOrUpdate(creditCard);
    }

    public void delete(CreditCard creditCard) {
        creditCardDao.delete(creditCard);
    }

    public List<CreditCard> getAll() {
        return creditCardDao.getAll();
    }

    public CreditCard findById(long id) {
        return creditCardDao.getByKey(id);
    }

    public void lockCard(CreditCard card) {
        if (card.getCreditCardId() > 0 && card.getBalance() < 0) {

            card = findById(card.getCreditCardId());
            card.setStatusId(1);
            creditCardDao.persist(card);
        }
    }

    public void unlockCard(CreditCard card) {
        if (card.getCreditCardId() > 0 && card.getStatusId() == 1) {
            card = findById(card.getCreditCardId());
            card.setStatusId(0);
            creditCardDao.persist(card);
        }
    }

    public List<CreditCard> lockedCardList() {
        return creditCardDao.lockedCardList();
    }

    public List<CreditCard> forLockCardList() {
        return creditCardDao.forLockCardList();
    }

}
