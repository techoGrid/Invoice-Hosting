package com.invoice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invoice.validator.ValidatorDao;

@Component
public class EntityNameValidator {

	@Autowired
	ValidatorDao validatorDao;
	
	public boolean isValid(String property, String value, String className, String activeField) {
		return validatorDao.isValid(property, value, className, activeField);
	}
	
	public boolean isValidUsername(String property, String value, String className) {
		return validatorDao.isValidUsername(property, value, className);
	}

	public boolean checkExistUserEmail(int userId, String email) {
		return validatorDao.checkExistUserEmail(userId,email);
	}

}
