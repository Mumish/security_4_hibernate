package by.it.dao;

import by.it.model.Payment;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("paymentDao")
public class PaymentDaoImpl extends AbstractDao<Long, Payment> implements PaymentDao {

    /**
     * переопределяем, т.к. стандартный валит ошибку
     *
     * @return
     */
    @Override
    public List<Payment> getAll() {

        String hql = "from Payment";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

}
