package by.it.dao;

import by.it.model.User;

public interface UserDao extends IBaseDao<Long, User> {

    User findByUserName(String userName);

}
