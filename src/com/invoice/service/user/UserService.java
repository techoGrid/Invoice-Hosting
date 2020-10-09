package com.invoice.service.user;

import java.util.List;

import com.invoice.bean.Designation;
import com.invoice.bean.User;


public interface UserService {
	
	public List<User> getUser(User user);
	
	public List<User> listUsers(User user);
	
	public List<User> getUser(String userName);
	
	public List<User> checkUserEmail(User user);
	
	public boolean saveUser(User user);
	
	public boolean deleteUser(User user);

	public User getUsersforEdit(User user);

	public List<Designation> getDesignationList(Designation designation);

	public List<?> getCEODetails();
	
	
}