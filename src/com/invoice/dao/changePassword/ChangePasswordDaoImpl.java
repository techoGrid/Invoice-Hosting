package com.invoice.dao.changePassword;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoice.bean.User;

@Repository
public class ChangePasswordDaoImpl implements ChangePasswordDao {
	@Autowired 
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkPassword(int userId, String password) {
		 Session session = getSession();
		 Transaction transaction = session.beginTransaction();
		 transaction.begin();
		 try{
			 Query query = session.createQuery("from User where userId=? and password=?");
			 query.setParameter(0, userId);
			 query.setParameter(1, password);
			 List<User> userList = query.list();
			 transaction.commit();
			 if(userList.size() != 0)
				 return true;
			 else
				 return false;
		 }
		 catch(HibernateException e) {
			 transaction.rollback();
			 e.printStackTrace();
			 return false;
		 }
		
	}

	@Override
	public boolean changePassword(int userId, String password) {
		Session session = getSession();
		 Transaction transaction = session.beginTransaction();
		 transaction.begin();
		 try{
			 System.out.println("userId:" + userId);
			 System.out.println("pwd:" + password);
			 Query query = session.createQuery("update User set password=? where userId=?");
			 query.setParameter(0, password);
			 query.setParameter(1, userId);
			 query.executeUpdate();
			 transaction.commit();
			 return true;
		 }
		 catch(HibernateException e) {
			 transaction.rollback();
			 e.printStackTrace();
			 return false;
		 }  
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkEmail(String forgotPasswordEmail) {
		 Session session = getSession();
		 Transaction transaction = session.beginTransaction();
		 transaction.begin();
		 try{
			 Query query = session.createQuery("from User where email=?");
			 query.setParameter(0, forgotPasswordEmail);
			 List<User> userList = query.list();
			 transaction.commit();
			 System.out.println(userList.size());
			 if(userList.size() != 0)
				 return true;
			 else
				 return false;
			 
 		 }
		 catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		 }
		 
	}
	
	@Override
	public boolean changeForgotPassword(String email, String password) {
		Session session = getSession();
		 Transaction transaction = session.beginTransaction();
		 transaction.begin();
		 try{
			 System.out.println("userId:" + email);
			 System.out.println("pwd:" + password);
			 Query query = session.createQuery("update User set password=? where email=?");
			 query.setParameter(0, password);
			 query.setParameter(1, email);
			 query.executeUpdate();
			 transaction.commit();
			 return true;
		 }
		 catch(HibernateException e) {
			 transaction.rollback();
			 e.printStackTrace();
			 return false;
		 }  
	}

	@Override
	public User getUserDetailsByUserId(int userId) {
		 User userDetails = null;
		 Session session = getSession();
		 Transaction transaction = session.beginTransaction();
		 transaction.begin();
		 try{
			 Query query = session.createQuery("FROM User WHERE userId=? AND active=?");
			 query.setParameter(0, userId);
			 query.setParameter(1, 1);
			 userDetails = (User) query.uniqueResult();
			 transaction.commit();
		 }catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		 }
		return userDetails;
	}

}
