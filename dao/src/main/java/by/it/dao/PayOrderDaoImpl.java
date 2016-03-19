package by.it.dao;

import by.it.model.PayOrder;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("payOrderDao")
public class PayOrderDaoImpl extends AbstractDao<Long, PayOrder> implements PayOrderDao {

    /**
     * переопределяем, т.к. стандартный валит ошибку
     *
     * @return
     */
    @Override
    public List<PayOrder> getAll() {

        String hql = "from PayOrder";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

}
