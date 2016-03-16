package by.it.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import by.it.model.User;
import java.util.List;
import org.hibernate.Query;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

    public User findByUserName(String userName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("userName", userName));
        return (User) criteria.uniqueResult();
    }

    /**
     * переопределяем, т.к. стандартный возвращает с неправильным join больше строк, чем надо
     * @return 
     */
    @Override
    public List<User> getAll() {

        String hql = "from User";
        Query query = getSession().createQuery(hql);
        return query.list();
    }

}
