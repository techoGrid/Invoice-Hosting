package com.invoice.validator;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ValidatorDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public boolean isValidUsername(String property, String value, String className) {
		List<?> rowList = null; 
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		Query query = session.createQuery("from "+className+" where "+property+"=?");
		query.setParameter(0, value);
		rowList = query.list();
		transaction.commit();
		if(rowList.size() > 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean isValid(String property, String value, String className, String activeField) {
		List<?> rowList = null; 
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		Query query = session.createQuery("from "+className+" where "+property+"=? and "+activeField+" = 1");
		query.setParameter(0, value);
		rowList = query.list();
		transaction.commit();
		if(rowList.size() > 0) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean checkExistUserEmail(int userId, String email) {
		List<?> rowList = null; 
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		Query query = session.createQuery("from User where userId = ? and email = ?");
		query.setParameter(0, userId);
		query.setParameter(1, email);
		rowList = query.list();
		transaction.commit();
		if(rowList.size() > 0) {
			return false;
		}else {
			return true;
		}
	}

	
}
