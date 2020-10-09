package com.invoice.service.changeUserPassword;

import com.invoice.bean.User;

public interface ChangeUserPasswordService {
	
	public boolean checkPassword(int userId, String password);
	 
	public boolean changePassword(int userId, String password);
	
	public boolean checkEmail(String forgotPasswordEmail);
	
	public boolean changeForgotPassword(String email,String password);

	public User getUserDetailsByUserId(int userId);
}
