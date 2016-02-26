package by.it.dao;

import by.it.model.User;

public interface UserDao {

	User findById(long id);
	
	User findByUserName(String userName);
	
}

