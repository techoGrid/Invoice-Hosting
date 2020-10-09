package com.invoice.dao.client;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoice.bean.Client;

@Repository
public class ClientDaoImpl implements ClientDao {

	@Autowired
	SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean clientSubmitDetails(Client client) {
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			session.saveOrUpdate(client);
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
	public List<Client> listClients(Client client) {
		List<Client> clientList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("from Client where active=1");
			clientList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return clientList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Client getClientsforEdit(Client client) {
		List<Client> clientList = null;
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		try {
			transaction.begin();
			Query query = session.createQuery("from Client where clientId = :clientId");
			query.setParameter("clientId", client.getClientId());
			clientList = query.list();
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		}
		return clientList.get(0);
	}

}
