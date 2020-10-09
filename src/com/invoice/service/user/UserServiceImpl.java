package com.invoice.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice.bean.Designation;
import com.invoice.bean.User;
import com.invoice.dao.user.UserDao;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	
	public List<User> getUser(User user) {		
		return userDao.getUser(user);
	}
	
	public List<User> listUsers(User user) {
		return userDao.listUsers(user);
	}
	
	public List<User> getUser(String userName) {
		return userDao.getUser(userName);
	}
	
	public List<User> checkUserEmail(User user) {
		return userDao.checkUserEmail(user);
	}
	
	public boolean saveUser(User user) {
		return userDao.saveUser(user);	
	}
	
	public boolean deleteUser(User user) {
		return userDao.deleteUser(user);		
	}

	@Override
	public User getUsersforEdit(User user) {
		return userDao.getUsersforEdit(user);
	}

	@Override
	public List<Designation> getDesignationList(Designation designation) {
		return userDao.getDesignationList(designation);
	}

	@Override
	public List<?> getCEODetails() {
		return userDao.getCEODetails();
	}

	
}