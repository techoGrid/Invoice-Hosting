package com.invoice.service.client;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoice.bean.Client;
import com.invoice.dao.client.ClientDao;


@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientDao clientDao;

	@Override
	public boolean clientSubmitDetails(Client client) {
		return clientDao.clientSubmitDetails(client);
	}

	@Override
	public List<Client> listClients(Client client) {
		return clientDao.listClients(client);
	}

	@Override
	public Client getClientsforEdit(Client client) {
		return clientDao.getClientsforEdit(client);
	}
	
	
}
