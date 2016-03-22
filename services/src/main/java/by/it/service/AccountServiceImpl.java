package by.it.service;

import by.it.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.it.model.Account;
import java.util.List;

@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    public void persist(Account account) {
        accountDao.persist(account);
    }

    public void saveOrUpdate(Account account) {
        accountDao.saveOrUpdate(account);
    }

    public void delete(Account account) {
        accountDao.delete(account);
    }

    public List<Account> getAll() {
        return accountDao.getAll();
    }

    public Account findById(long id) {
        return accountDao.getByKey(id);
    }

    public void lockCardByAdmin(Account card) {
        if (card.getAccountId() > 0 && card.getBalance() < 0 && card.getStatusId() == 0) {

            card = findById(card.getAccountId());
            card.setStatusId(1);
            accountDao.persist(card);
        }
    }

    public void unlockCardByAdmin(Account card) {
        if (card.getAccountId() > 0 && card.getStatusId() == 1) {
            card = findById(card.getAccountId());
            card.setStatusId(0);
            accountDao.persist(card);
        }
    }

    public void lockCardByUser(Account card) {
        if (card.getAccountId() > 0 && card.getStatusId() == 0) {

            card = findById(card.getAccountId());
            card.setStatusId(2);
            accountDao.persist(card);
        }
    }

    public void unlockCardByUser(Account card) {
        if (card.getAccountId() > 0 && card.getStatusId() == 2) {
            card = findById(card.getAccountId());
            card.setStatusId(0);
            accountDao.persist(card);
        }
    }

    public void annulAccountByUser(Account account) {
        if (account.getAccountId() > 0 && account.getStatusId() == 0) {

            account = findById(account.getAccountId());
            account.setStatusId(1);
            accountDao.persist(account);
        }
    }

    public void activeAccountByUser(Account account) {
        if (account.getAccountId() > 0 && account.getStatusId() == 1) {
            account = findById(account.getAccountId());
            account.setStatusId(0);
            accountDao.persist(account);
        }
    }

}
