package by.it.dao;

import by.it.model.CreditCard;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("creditCardDao")
public class CreditCardDaoImpl extends AbstractDao<Long, CreditCard> implements CreditCardDao {

    /**
     * переопределяем, т.к. стандартный валит ошибку
     *
     * @return
     */
    @Override
    public List<CreditCard> getAll() {

        String hql = "from CreditCard";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    public List<CreditCard> lockedCardList() {
        String hql = "from CreditCard c where c.statusId=1";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    public List<CreditCard> forLockCardList() {
                String hql = "from CreditCard c where c.balance<0 AND c.statusId=0";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

}
