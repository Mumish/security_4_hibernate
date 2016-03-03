package by.it.dao;

import by.it.model.User;

public interface UserDao extends IBaseDao<Long, User> {

    //TODO:метод скрыт, т.к. в обычном DAO есть стандартный
    //User findById(long id);
    User findByUserName(String userName);

}
