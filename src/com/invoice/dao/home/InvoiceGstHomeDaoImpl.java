package com.invoice.dao.home;

import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoice.bean.Client;
import com.invoice.bean.InvoiceGST;
import com.invoice.bean.User;


@Repository
public class InvoiceGstHomeDaoImpl implements InvoiceGstHomeDao{

	@Autowired
	SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getClientNameList(Client client) {
		List<Client> clientNameList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try{
			transaction.begin();
			Query query = session.createQuery("from Client where active=1");
			clientNameList = query.list();
			transaction.commit();
		}catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return clientNameList;
	}

	@Override
	public Client getClientsDetailsById(int clientId) {
		Client client = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("FROM Client WHERE clientId=? AND active=1");
			query.setParameter(0, clientId);
			client = (Client) query.uniqueResult();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return client;
	}

	@Override
	public User getUserDetailsById(int userId) {
		User user = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("FROM User WHERE userId=? AND active=1");
			query.setParameter(0,userId);
			user = (User) query.uniqueResult();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean submitServiceInvoiceCreationRegular(InvoiceGST invoice) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(invoice);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean submitServiceInvoiceCreationOneTime(InvoiceGST invoice) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(invoice);
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getMaxInvoiceNo(String financialYear) {
		String maxInvoiceNo = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try{
			transaction.begin();
			Query query = session.createQuery("SELECT MAX(invoiceNo) FROM InvoiceGST WHERE financialYear=?");
			query.setParameter(0, financialYear);
			maxInvoiceNo = (String) query.uniqueResult();
			transaction.commit();
		}catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return maxInvoiceNo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceGST> getInvoiceListAllDetails() {
		List<InvoiceGST> invoiceList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try{
			transaction.begin();
			Query query = session.createQuery("FROM InvoiceGST WHERE invoiceFlag IN('Open','Modify') AND invoiceType IN('Service Invoice')");
			invoiceList = query.list();
			transaction.commit();
		}catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return invoiceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceGST> getInvoiceListDetails(int userId) {
		List<InvoiceGST> invoiceList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try{
			transaction.begin();
			Query query = session.createQuery("FROM InvoiceGST WHERE invoiceFlag IN('Open','Modify') AND invoiceType IN('Service Invoice') AND User.userId=?");
			query.setParameter(0, userId);
			invoiceList = query.list();
			transaction.commit();
		}catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return invoiceList;
	}

	@Override
	public boolean deletingServiceInvoiceDetails(int invoiceId) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("UPDATE InvoiceGST SET invoiceFlag=? WHERE invoiceId=? AND invoiceFlag IN('Open','Modify') AND invoiceType IN('Service Invoice')");
			query.setParameter(0, "Deleted");
			query.setParameter(1, invoiceId);
			query.executeUpdate();
			transaction.commit();
			return true;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public InvoiceGST getInvoiceDetailsById(int invoiceId) {
		InvoiceGST invoiceDetails = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("FROM InvoiceGST WHERE invoiceId=?");
			query.setParameter(0, invoiceId);
			invoiceDetails = (InvoiceGST) query.uniqueResult();
			transaction.commit();
		}catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return invoiceDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceGST> getInvoiceDetailsById(String fromDate, String toDate) {
		List<InvoiceGST> invoiceList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try{
			transaction.begin();
			Query query = session.createQuery("FROM InvoiceGST WHERE invoiceType IN('Service Invoice')"
					+ " AND invoiceDate between ? and ? order by invoiceDate asc");
			query.setParameter(0, fromDate);
			query.setParameter(1, toDate);
			invoiceList = query.list();
			transaction.commit();
		}catch(HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return invoiceList;
	}

	
	
	
}
