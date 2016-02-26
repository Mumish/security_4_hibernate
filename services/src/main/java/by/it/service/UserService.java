package by.it.service;

import by.it.model.User;

public interface UserService {

	User findById(int id);
	
	User findByUserName(String userName);
	
}