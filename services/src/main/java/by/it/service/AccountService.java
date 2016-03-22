package by.it.service;

import by.it.model.Account;

public interface AccountService extends DataService<Account> {

    void annulAccountByUser(Account account);

    void activeAccountByUser(Account account);

}
