package com.invoice.dao.client;

import java.util.List;

import com.invoice.bean.Client;

public interface ClientDao {

	boolean clientSubmitDetails(Client client);

	List<Client> listClients(Client client);

	Client getClientsforEdit(Client client);


	

}
