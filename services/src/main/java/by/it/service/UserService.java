package by.it.service;

import by.it.model.PayOrder;
import by.it.model.User;

public interface UserService extends DataService<User> {

    User findByUserName(String userName);

    void createAdminIfNeed();

    void saveNewUser(User user);

    void payOrder(PayOrder payOrder);

    void transfer(PayOrder payOrder);

}
