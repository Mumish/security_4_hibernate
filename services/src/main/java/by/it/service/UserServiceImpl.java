package by.it.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.it.dao.UserDao;
import by.it.model.User;
import by.it.model.UserProfile;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    public User findById(long id) {
        return dao.getByKey(id);
    }

    public User findByUserName(String userName) {
        saveIt();
        return dao.findByUserName(userName);
    }

    public void persist(User user) {
        dao.persist(user);
    }

    public void delete(User user) {
        dao.delete(user);
    }

    private void saveIt() {

        User u = new User();

        u.setUserName("UserName11");

        u.setPassword("pass");

        u.setFirstName("first1");

        u.setLastName("last1");

        u.setEmail("email1");

        UserProfile prof = new UserProfile();
        u.getUserProfiles().add(prof);

        persist(u);
//        saveOrUpdate(u);
    }
}
