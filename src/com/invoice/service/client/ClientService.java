package com.invoice.service.client;

import java.util.List;

import com.invoice.bean.Client;

public interface ClientService {

	boolean clientSubmitDetails(Client client);

	List<Client> listClients(Client client);

	Client getClientsforEdit(Client client);

	
}
