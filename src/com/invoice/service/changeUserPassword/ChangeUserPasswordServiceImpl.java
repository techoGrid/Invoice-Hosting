package com.invoice.service.changeUserPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice.bean.User;
import com.invoice.dao.changePassword.ChangePasswordDao;


@Service
public class ChangeUserPasswordServiceImpl implements ChangeUserPasswordService {

	@Autowired
	ChangePasswordDao changePasswordDao;
	
	@Override
	public boolean checkPassword(int userId, String password) {		
		return changePasswordDao.checkPassword(userId, password);
	}

	@Override
	public boolean changePassword(int userId, String password) {
		return changePasswordDao.changePassword(userId, password);
	}

	@Override
	public boolean checkEmail(String forgotPasswordEmail) {
		return  changePasswordDao.checkEmail(forgotPasswordEmail);
	}

	@Override
	public boolean changeForgotPassword(String email, String password) {
	 	return changePasswordDao.changeForgotPassword(email, password);
	}

	@Override
	public User getUserDetailsByUserId(int userId) {
		return changePasswordDao.getUserDetailsByUserId(userId);
	}
	
}
