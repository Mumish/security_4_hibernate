package by.it.service;

import by.it.dao.PayOrderDao;
import by.it.dao.PaymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.it.dao.UserDao;
import by.it.dao.UserProfileDao;
import by.it.model.Account;
import by.it.model.CreditCard;
import by.it.model.PayOrder;
import by.it.model.Payment;
import by.it.model.User;
import by.it.model.UserProfile;
import by.it.model.enums.UserProfileType;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao profileDao;
    @Autowired
    private PayOrderDao orderDao;
    @Autowired
    private PaymentDao paymentDao;

    public User findById(long id) {
        return userDao.getByKey(id);
    }

    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    public void persist(User user) {
        userDao.persist(user);
    }

    public void saveOrUpdate(User user) {
        userDao.saveOrUpdate(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void createAdminIfNeed() {
        User u = findById(1L);

        if (u == null) {

            u = new User();

            u.setUserName("root");

            u.setPassword("root");

            u.setFirstName("admin_first_name");

            u.setLastName("admin_last_name");

            u.setEmail("admin@email.com");

            UserProfile profAdmin = getUserProfile(UserProfileType.ADMIN);

            u.getUserProfiles().add(profAdmin);
            UserProfile profDba = getUserProfile(UserProfileType.DBA);
            u.getUserProfiles().add(profDba);

            persist(u);
        }
    }

    public void saveNewUser(User user) {

        UserProfile prof = getUserProfile(UserProfileType.USER);

        user.getUserProfiles().add(prof);

        //TODO:генерация новых объектов, заполенных
        //можно через спринг инициализировать
        CreditCard cred = new CreditCard();
        Account acc = new Account();

        user.setAccount(acc);
        user.setCreditCard(cred);

        acc.setUser(user);
        cred.setUser(user);

        persist(user);
    }

    private UserProfile getUserProfile(UserProfileType type) {
        UserProfile profile = profileDao.findByType(type.getType());

        if (profile == null) {
            profile = new UserProfile();
            profile.setType(type.getType());
            profileDao.persist(profile);
        }

        return profile;
    }

    public void payOrder(PayOrder payOrder) {

        Payment pay = new Payment();

        pay.setDatePayment(new Date());
        pay.setOrder(payOrder);
        payOrder.setPayment(pay);
        paymentDao.persist(pay);

        User us = findById(payOrder.getUser().getId());
        us.getCreditCard().setBalance(us.getCreditCard().getBalance() - payOrder.getPrice());
        persist(us);
    }

    public void transfer(PayOrder payOrder) {

        User usSource = findById(payOrder.getId());
        usSource.getCreditCard().setBalance(usSource.getCreditCard().getBalance() - payOrder.getPrice());
        persist(usSource);
        User usDest = findById(payOrder.getUser().getId());
        usSource.getAccount().setBalance(usSource.getAccount().getBalance() + payOrder.getPrice());
        persist(usDest);
    }
}
