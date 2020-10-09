package com.invoice.dao.user;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoice.bean.Designation;
import com.invoice.bean.User;


@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUser(User user) {
		List<User> userList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", user.getUsername());
			userList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return userList;
	}

	@SuppressWarnings("unchecked")
	public List<User> listUsers(User user) {
		List<User> userList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session
					.createQuery("FROM User WHERE active=1 AND designation.designationName NOT IN('ADMIN')");
			userList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return userList;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUser(String userName) {
		List<User> userList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session
					.createQuery("from User where username = :username");
			query.setParameter("username", userName);
			userList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return userList;
	}

	@SuppressWarnings("unchecked")
	public List<User> checkUserEmail(User user) {
		List<User> userList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session
					.createQuery("from User where email = :useremail");
			query.setParameter("useremail", user.getEmail());
			userList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}

		return userList;
	}

	public boolean saveUser(User user) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(user);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();

		try {
			transaction.begin();
			Query query = session
					.createQuery("update User set active=0 where userId=?");
			query.setParameter(0, user.getUserId());
			query.executeUpdate();
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUsersforEdit(User user) {
		List<User> userList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session
					.createQuery("from User where userId = :userId");
			query.setParameter("userId", user.getUserId());
			userList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return userList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Designation> getDesignationList(Designation designation) {
		List<Designation> desigList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("from Designation where active = 1");
			desigList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return desigList;
	}

	@Override
	public List<?> getCEODetails() {
		List<?> getCEO = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session
					.createQuery("from User where designation.designationName=?");
			query.setParameter(0, "CEO");
			getCEO = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return getCEO;
	}

 }
