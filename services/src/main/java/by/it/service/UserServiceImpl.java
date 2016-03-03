package by.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.it.dao.UserDao;
import by.it.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    public User findById(long id) {
        return dao.getByKey(id);
    }

    public User findByUserName(String userName) {
        return dao.findByUserName(userName);
    }

    public void save(User user) {
        dao.persist(user);
    }

    public void delete(User user) {
        dao.delete(user);
    }

}
