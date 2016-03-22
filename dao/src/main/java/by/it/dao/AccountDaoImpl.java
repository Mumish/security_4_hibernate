package by.it.dao;

import by.it.model.Account;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("accountDao")
public class AccountDaoImpl extends AbstractDao<Long, Account> implements AccountDao {

    /**
     * переопределяем, т.к. стандартный валит ошибку
     *
     * @return
     */
    @Override
    public List<Account> getAll() {

        String hql = "from Account";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

}
